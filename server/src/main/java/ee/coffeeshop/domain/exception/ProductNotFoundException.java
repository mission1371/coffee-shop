package ee.coffeeshop.domain.exception;

public class ProductNotFoundException extends BusinessException {

	private static final String CODE = "product-not-found";

	public ProductNotFoundException() {
		this("Product not found");
	}

	public ProductNotFoundException(final String message) {
		super(message, CODE);
	}
}
