package ee.coffeeshop.domain.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
	private final String key;

	public BusinessException(final String message, final String key) {
		super(message);
		this.key = key;
	}
}
