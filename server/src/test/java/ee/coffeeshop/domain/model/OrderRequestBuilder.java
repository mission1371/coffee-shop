package ee.coffeeshop.domain.model;

import java.math.BigDecimal;
import java.util.List;

import static ee.coffeeshop.domain.model.OrderItemRequestBuilder.anOrderItemRequest;

public class OrderRequestBuilder extends OrderRequest.OrderRequestBuilder {

	public static OrderRequest.OrderRequestBuilder anOrderRequest() {
		return new OrderRequestBuilder().amount(BigDecimal.ONE).items(List.of(anOrderItemRequest().build()));
	}
}