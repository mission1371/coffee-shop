package ee.coffeeshop.domain.model;

public class OrderItemRequestBuilder extends OrderItemRequest.OrderItemRequestBuilder {

	public static OrderItemRequest.OrderItemRequestBuilder anOrderItemRequest() {
		return new OrderItemRequestBuilder().productId(1L).quantity(1);
	}

}