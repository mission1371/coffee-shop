package ee.coffeeshop.domain.exception;

public class InsufficientAmountException extends BusinessException {

	private static final String CODE = "insufficient-amount";

	public InsufficientAmountException() {
		this("Insufficient amount");
	}

	public InsufficientAmountException(final String message) {
		super(message, CODE);
	}
}
