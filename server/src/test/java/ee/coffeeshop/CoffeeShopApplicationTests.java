package ee.coffeeshop;

import ee.coffeeshop.domain.service.AccountService;
import ee.coffeeshop.persistence.AbstractPersistenceIntegrationTest;
import ee.coffeeshop.persistence.product.ProductEntity;
import ee.coffeeshop.persistence.product.ProductEntityRepository;
import ee.coffeeshop.rest.order.OrderItemResource;
import ee.coffeeshop.rest.order.OrderResource;
import ee.coffeeshop.rest.product.ProductUpdateResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static ee.coffeeshop.persistence.product.ProductEntityBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.OK;

@WithMockUser
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CoffeeShopApplicationTests extends AbstractPersistenceIntegrationTest {

	@MockBean
	private AccountService accountService;

	@Autowired
	private ProductEntityRepository repository;

	@Autowired
	private TestRestTemplate template;

	@BeforeEach
	void setup() {
		when(accountService.getAccountId()).thenReturn(Mono.just("1"));
		StepVerifier.create(repository.deleteAll()).verifyComplete();
	}

	@Test
	public void shouldFetchProducts() {
		// given
		insertProducts(muffin(), brownie(), shirt());

		// when
		final ResponseEntity<Object[]> result = template.getForEntity("/products", Object[].class);

		// then
		assertThat(result.getStatusCode()).isEqualTo(OK);
		assertThat(result.getBody().length).isEqualTo(3);
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void shouldUpdateProductStock() {
		// given
		final ProductEntity brownie = brownie();
		insertProducts(brownie);
		final ProductUpdateResource resource = new ProductUpdateResource();
		resource.setStock(5);

		// when
		final ResponseEntity<Object> result = template.exchange("/products/" + brownie.getId(), PUT,
				new HttpEntity<>(resource, getHeadersWithCsrfToken()), Object.class);

		// then
		assertThat(result.getStatusCode()).isEqualTo(OK);
		repository.findById(brownie.getId()).as(StepVerifier::create)
				.assertNext(productEntity -> assertThat(productEntity.getStock()).isEqualTo(brownie.getStock() + 5))
				.verifyComplete();
	}

	@Test
	public void shouldPlaceOrder() {
		// given
		final ProductEntity muffin = muffin();
		insertProducts(muffin);
		final OrderItemResource itemRequest = OrderItemResource.builder().quantity(1).productId(muffin.getId()).build();
		final OrderResource request = OrderResource.builder().amount(BigDecimal.TEN).items(List.of(itemRequest))
				.build();

		// when
		final ResponseEntity<Void> result = template.exchange("/orders", POST,
				new HttpEntity<>(request, getHeadersWithCsrfToken()), Void.class);

		// then
		assertThat(result.getStatusCode()).isEqualTo(OK);
		repository.findById(muffin.getId()).as(StepVerifier::create)
				.assertNext(productEntity -> assertThat(productEntity.getStock()).isEqualTo(muffin.getStock() - 1))
				.verifyComplete();
	}

	private void insertProducts(final ProductEntity... entities) {
		this.repository.saveAll(Arrays.asList(entities)).as(StepVerifier::create).expectNextCount(entities.length)
				.verifyComplete();
	}

	private HttpHeaders getHeadersWithCsrfToken() {
		HttpHeaders headers = new HttpHeaders();
		final String token = UUID.randomUUID().toString();
		headers.add("X-XSRF-TOKEN", token);
		headers.add(HttpHeaders.COOKIE, "XSRF-TOKEN=" + token);
		return headers;
	}
}
