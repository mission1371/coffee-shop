package ee.coffeeshop.domain.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductNotFoundExceptionUnitTest {
	@Test
	void shouldCreate() {
		// when
		final ProductNotFoundException exception = new ProductNotFoundException();

		// then
		assertThat(exception.getKey()).isEqualTo("product-not-found");
		assertThat(exception.getMessage()).isEqualTo("Product not found");
	}
}