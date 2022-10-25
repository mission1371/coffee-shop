package ee.coffeeshop.rest.exception;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
class ErrorResource {

	String message;
	String code;
}
