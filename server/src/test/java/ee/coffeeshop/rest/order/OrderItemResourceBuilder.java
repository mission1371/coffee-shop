package ee.coffeeshop.rest.order;

class OrderItemResourceBuilder extends OrderItemResource.OrderItemResourceBuilder {

	public static OrderItemResource.OrderItemResourceBuilder anOrderItemResource() {
		return new OrderItemResourceBuilder().productId(1L).quantity(1);
	}
}