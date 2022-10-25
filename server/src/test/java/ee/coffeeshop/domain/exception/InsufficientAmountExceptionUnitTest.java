package ee.coffeeshop.domain.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InsufficientAmountExceptionUnitTest {

	@Test
	void shouldCreate() {
		// when
		final InsufficientAmountException exception = new InsufficientAmountException();

		// then
		assertThat(exception.getKey()).isEqualTo("insufficient-amount");
		assertThat(exception.getMessage()).isEqualTo("Insufficient amount");
	}

}