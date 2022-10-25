package ee.coffeeshop.rest.product;

import ee.coffeeshop.domain.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import reactor.core.publisher.Flux;

import java.util.List;

import static ee.coffeeshop.domain.model.ProductBuilder.brownie;
import static ee.coffeeshop.domain.model.ProductBuilder.muffin;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WithMockUser
@WebFluxTest(controllers = ProductRestController.class)
class ProductRestControllerIntegrationTest {
	@MockBean
	private ProductService service;

	@MockBean
	private ProductResourceConverter converter;

	@Autowired
	private WebTestClient webClient;

	@Test
	void shouldReturnProducts() {
		// given
		when(converter.convert(any())).thenCallRealMethod();
		when(service.getByType(any())).thenReturn(Flux.fromIterable(List.of(brownie(), muffin())));

		// when
		final ResponseSpec spec = webClient.get().uri(uri -> uri.path("/products").queryParam("type", "1").build())
				.exchange();

		// then
		spec.expectStatus().isOk();
		spec.expectBody().jsonPath("$[0].name").isEqualTo("Brownie").jsonPath("$[0].price").isEqualTo("0.65")
				.jsonPath("$[0].currencyCode").isEqualTo("EUR").jsonPath("$[1].name").isEqualTo("Muffin")
				.jsonPath("$[1].price").isEqualTo("1").jsonPath("$[1].currencyCode").isEqualTo("EUR");

	}

	@Test
	void shouldValidateNullProductType() {
		// given
		final Long type = null;

		// when
		final ResponseSpec spec = webClient.get().uri(uri -> uri.path("/products").queryParam("type", type).build())
				.exchange();

		// then
		spec.expectStatus().isBadRequest();
	}
}