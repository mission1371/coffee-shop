package ee.coffeeshop.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderItemRequest {
	Long productId;
	int quantity;
}
