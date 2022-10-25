package ee.coffeeshop.persistence.product;

import ee.coffeeshop.domain.model.Product;
import org.junit.jupiter.api.Test;

import static ee.coffeeshop.persistence.product.ProductEntityBuilder.muffin;
import static org.assertj.core.api.Assertions.assertThat;

class ProductEntityConverterUnitTest {

	private final ProductEntityConverter converter = new ProductEntityConverter();

	@Test
	void shouldConvert() {
		// given
		final ProductEntity entity = muffin();

		// when
		final Product product = converter.convert(entity);

		// then
		assertThat(product.getId()).isEqualTo(entity.getId());
		assertThat(product.getType()).isEqualTo(entity.getType());
		assertThat(product.getName()).isEqualTo(entity.getName());
		assertThat(product.getStock()).isEqualTo(entity.getStock());
		assertThat(product.getPrice()).isEqualTo(entity.getPrice());
		assertThat(product.getCurrencyCode()).isEqualTo(entity.getCurrencyCode());
		assertThat(product.getImageUrl()).isEqualTo(entity.getImageUrl());
	}

}