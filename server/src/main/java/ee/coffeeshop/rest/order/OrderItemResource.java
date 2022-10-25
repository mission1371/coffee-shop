package ee.coffeeshop.rest.order;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResource {

	@NotNull(message = "Product id required")
	private Long productId;

	@Min(1)
	@Max(100)
	private int quantity;
}
