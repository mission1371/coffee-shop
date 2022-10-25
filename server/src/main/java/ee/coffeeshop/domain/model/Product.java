package ee.coffeeshop.domain.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class Product {
	Long id;
	Long type;
	String name;
	int stock;
	BigDecimal price;
	String currencyCode;
	String imageUrl;
}