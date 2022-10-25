package ee.coffeeshop.persistence.product;

import java.math.BigDecimal;

public class ProductEntityBuilder extends ProductEntity.ProductEntityBuilder<ProductEntity, ProductEntityBuilder> {

	public static ProductEntity muffin() {
		return new ProductEntityBuilder().type(1L).name("Muffin").stock(10).price(BigDecimal.TEN).currencyCode("EUR")
				.build();
	}

	public static ProductEntity brownie() {
		return new ProductEntityBuilder().type(1L).name("Brownie").stock(10).price(BigDecimal.TEN).currencyCode("EUR")
				.build();
	}

	public static ProductEntity shirt() {
		return new ProductEntityBuilder().type(2L).name("Shirt").stock(10).price(BigDecimal.TEN).currencyCode("EUR")
				.build();
	}

	@Override
	protected ProductEntityBuilder self() {
		return this;
	}

	@Override
	public ProductEntity build() {
		return new ProductEntity(this);
	}
}