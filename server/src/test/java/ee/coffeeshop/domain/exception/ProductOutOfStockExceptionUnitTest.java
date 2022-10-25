package ee.coffeeshop.domain.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductOutOfStockExceptionUnitTest {
	@Test
	void shouldCreate() {
		// when
		final ProductOutOfStockException exception = new ProductOutOfStockException();

		// then
		assertThat(exception.getKey()).isEqualTo("product-out-of-stock");
		assertThat(exception.getMessage()).isEqualTo("Product out of stock");
	}
}