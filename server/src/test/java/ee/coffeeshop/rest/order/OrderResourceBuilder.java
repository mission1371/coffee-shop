package ee.coffeeshop.rest.order;

import java.math.BigDecimal;
import java.util.List;

import static ee.coffeeshop.rest.order.OrderItemResourceBuilder.anOrderItemResource;

class OrderResourceBuilder extends OrderResource.OrderResourceBuilder {

	public static OrderResource.OrderResourceBuilder anOrderResource() {
		return new OrderResourceBuilder().items(List.of(anOrderItemResource().build())).amount(BigDecimal.ONE);
	}

}