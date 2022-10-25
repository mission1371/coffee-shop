package ee.coffeeshop.rest.order;

import ee.coffeeshop.domain.model.OrderRequest;
import ee.coffeeshop.domain.service.OrderService;
import ee.coffeeshop.rest.exception.RestExceptionHandler;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static ee.coffeeshop.domain.model.OrderRequestBuilder.anOrderRequest;
import static ee.coffeeshop.rest.order.OrderItemResourceBuilder.anOrderItemResource;
import static ee.coffeeshop.rest.order.OrderResourceBuilder.anOrderResource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@WithMockUser
@WebFluxTest(controllers = OrderRestController.class)
class OrderRestControllerIntegrationTest {

	@MockBean
	private OrderService service;

	@MockBean
	private OrderResourceConverter converter;

	@SpyBean
	private RestExceptionHandler restExceptionHandler;

	@Captor
	private ArgumentCaptor<WebExchangeBindException> captor;

	@Autowired
	private WebTestClient webClient;

	@Test
	void shouldNotAcceptNullOrder() {
		// when
		final ResponseSpec spec = webClient.mutateWith(csrf()).post().uri(uri -> uri.path("/orders").build()).body(null)
				.exchange();

		// then
		spec.expectStatus().isBadRequest();
	}

	@Test
	void shouldNotAcceptNullOrderItems() {
		// given
		final OrderResource resource = anOrderResource().items(null).build();

		// when
		final ResponseSpec spec = webClient.mutateWith(csrf()).post().uri(uri -> uri.path("/orders").build())
				.bodyValue(resource).exchange();

		// then
		spec.expectStatus().isBadRequest();
		verify(restExceptionHandler).webExchangeBindException(captor.capture());
		assertThat(captor.getValue().getMessage()).contains("Order items required");
	}

	@Test
	void shouldNotAcceptEmptyOrderItems() {
		// given
		final OrderResource resource = anOrderResource().items(Collections.emptyList()).build();

		// when
		final ResponseSpec spec = webClient.mutateWith(csrf()).post().uri(uri -> uri.path("/orders").build())
				.bodyValue(resource).exchange();

		// then
		spec.expectStatus().isBadRequest();
		verify(restExceptionHandler).webExchangeBindException(captor.capture());
		assertThat(captor.getValue().getMessage()).contains("size must be between 1 and 100");
	}

	@Test
	void shouldNotAcceptNullAmount() {
		// given
		final OrderResource resource = anOrderResource().amount(null).build();

		// when
		final ResponseSpec spec = webClient.mutateWith(csrf()).post().uri(uri -> uri.path("/orders").build())
				.bodyValue(resource).exchange();

		// then
		spec.expectStatus().isBadRequest();
		verify(restExceptionHandler).webExchangeBindException(captor.capture());
		assertThat(captor.getValue().getMessage()).contains("Amount is required");
	}

	@Test
	void shouldNotAcceptZeroAmount() {
		// given
		final OrderResource resource = anOrderResource().amount(BigDecimal.ZERO).build();

		// when
		final ResponseSpec spec = webClient.mutateWith(csrf()).post().uri(uri -> uri.path("/orders").build())
				.bodyValue(resource).exchange();

		// then
		spec.expectStatus().isBadRequest();
		verify(restExceptionHandler).webExchangeBindException(captor.capture());
		assertThat(captor.getValue().getMessage()).contains("must be greater than or equal to 0.01");
	}

	@Test
	void shouldNotAcceptNegativeAmount() {
		// given
		final OrderResource resource = anOrderResource().amount(BigDecimal.valueOf(-1L)).build();

		// when
		final ResponseSpec spec = webClient.mutateWith(csrf()).post().uri(uri -> uri.path("/orders").build())
				.bodyValue(resource).exchange();

		// then
		spec.expectStatus().isBadRequest();
		verify(restExceptionHandler).webExchangeBindException(captor.capture());
		assertThat(captor.getValue().getMessage()).contains("must be greater than or equal to 0.01");
	}

	@Test
	void shouldNotAcceptTooMuchAmount() {
		// given
		final OrderResource resource = anOrderResource().amount(BigDecimal.valueOf(1000000)).build();

		// when
		final ResponseSpec spec = webClient.mutateWith(csrf()).post().uri(uri -> uri.path("/orders").build())
				.bodyValue(resource).exchange();

		// then
		spec.expectStatus().isBadRequest();
		verify(restExceptionHandler).webExchangeBindException(captor.capture());
		assertThat(captor.getValue().getMessage()).contains("must be less than or equal to 999999.99");
	}

	@Test
	void shouldNotAcceptZeroItemQuantity() {
		// given
		final OrderItemResource item = anOrderItemResource().quantity(0).build();
		final OrderResource resource = anOrderResource().items(List.of(item)).build();

		// when
		final ResponseSpec spec = webClient.mutateWith(csrf()).post().uri(uri -> uri.path("/orders").build())
				.bodyValue(resource).exchange();

		// then
		spec.expectStatus().isBadRequest();
		verify(restExceptionHandler).webExchangeBindException(captor.capture());
		assertThat(captor.getValue().getMessage()).contains("must be greater than or equal to 1");
	}

	@Test
	void shouldNotAcceptNegativeItemQuantity() {
		// given
		final OrderItemResource item = anOrderItemResource().quantity(-1).build();
		final OrderResource resource = anOrderResource().items(List.of(item)).build();

		// when
		final ResponseSpec spec = webClient.mutateWith(csrf()).post().uri(uri -> uri.path("/orders").build())
				.bodyValue(resource).exchange();

		// then
		spec.expectStatus().isBadRequest();
		verify(restExceptionHandler).webExchangeBindException(captor.capture());
		assertThat(captor.getValue().getMessage()).contains("must be greater than or equal to 1");
	}

	@Test
	void shouldNotAcceptNullItemProductId() {
		// given
		final OrderItemResource item = anOrderItemResource().productId(null).build();
		final OrderResource resource = anOrderResource().items(List.of(item)).build();

		// when
		final ResponseSpec spec = webClient.mutateWith(csrf()).post().uri(uri -> uri.path("/orders").build())
				.bodyValue(resource).exchange();

		// then
		spec.expectStatus().isBadRequest();
		verify(restExceptionHandler).webExchangeBindException(captor.capture());
		assertThat(captor.getValue().getMessage()).contains("Product id required");
	}

	@Test
	void shouldPlaceOrder() {
		// given
		final OrderItemResource item = anOrderItemResource().build();
		final OrderResource resource = anOrderResource().items(List.of(item)).build();
		final OrderRequest order = anOrderRequest().build();
		when(converter.convert(any())).thenReturn(order);

		// when
		final ResponseSpec spec = webClient.mutateWith(csrf()).post().uri(uri -> uri.path("/orders").build())
				.bodyValue(resource).exchange();

		// then
		spec.expectStatus().isOk().expectBody().isEmpty();
		verify(service).place(eq(order));
	}

}