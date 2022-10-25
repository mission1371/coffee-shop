package ee.coffeeshop.domain.exception;

public class ProductOutOfStockException extends BusinessException {
	private static final String CODE = "product-out-of-stock";

	public ProductOutOfStockException() {
		this("Product out of stock");
	}

	public ProductOutOfStockException(final String message) {
		super(message, CODE);
	}
}
