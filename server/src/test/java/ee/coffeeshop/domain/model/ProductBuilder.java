package ee.coffeeshop.domain.model;

import java.math.BigDecimal;

public class ProductBuilder extends Product.ProductBuilder {

	public static Product brownie() {
		return new ProductBuilder().type(1L).id(1L).name("Brownie").price(BigDecimal.valueOf(0.65)).currencyCode("EUR")
				.build();
	}

	public static Product muffin() {
		return new ProductBuilder().type(1L).id(2L).name("Muffin").price(BigDecimal.ONE).currencyCode("EUR").build();
	}
}