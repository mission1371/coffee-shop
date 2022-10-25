package ee.coffeeshop.rest.order;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResource {

	@NotNull(message = "Amount is required")
	@DecimalMin(value = "0.01")
	@DecimalMax(value = "999999.99")
	private BigDecimal amount;

	@NotNull(message = "Order items required")
	@Size(min = 1, max = 100)
	private List<@Valid OrderItemResource> items;
}
