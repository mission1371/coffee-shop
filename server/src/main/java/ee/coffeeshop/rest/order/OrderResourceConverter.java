package ee.coffeeshop.rest.order;

import ee.coffeeshop.domain.model.OrderItemRequest;
import ee.coffeeshop.domain.model.OrderRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class OrderResourceConverter {
	public OrderRequest convert(final OrderResource resource) {
		return OrderRequest.builder().amount(resource.getAmount()).items(convertItems(resource.getItems())).build();
	}

	private List<OrderItemRequest> convertItems(final List<OrderItemResource> items) {
		return items.stream().map(this::convertItem).toList();
	}

	private OrderItemRequest convertItem(final OrderItemResource orderItemResource) {
		return OrderItemRequest.builder().productId(orderItemResource.getProductId())
				.quantity(orderItemResource.getQuantity()).build();
	}
}
