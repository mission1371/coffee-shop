package ee.coffeeshop.rest.product;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
class ProductResource {
	Long id;
	String name;
	long type;
	BigDecimal price;
	String currencyCode;
	long stock;
	String imageUrl;

}