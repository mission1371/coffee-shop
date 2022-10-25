package ee.coffeeshop.domain.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
public class OrderRequest {

	BigDecimal amount;
	List<OrderItemRequest> items;
}
