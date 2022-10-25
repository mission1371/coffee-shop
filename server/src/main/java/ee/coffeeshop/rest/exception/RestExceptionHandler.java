package ee.coffeeshop.rest.exception;

import ee.coffeeshop.domain.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {
	private static final String GENERIC_EXCEPTION_CODE = "generic-exception";

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(WebExchangeBindException.class)
	public ResponseEntity<ErrorResource> webExchangeBindException(final WebExchangeBindException exception) {
		log.error(exception.getMessage());
		final StringBuilder error = new StringBuilder();
		exception.getAllErrors().forEach(e -> error.append(e.getDefaultMessage()));
		return ResponseEntity.badRequest().body(ErrorResource.builder().message(error.toString()).build());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResource> handleBusinessException(final BusinessException exception) {
		log.error(exception.getMessage());
		return ResponseEntity.badRequest()
				.body(ErrorResource.builder().message(exception.getMessage()).code(exception.getKey()).build());
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResource> handleGenericException(final RuntimeException exception) {
		log.error(exception.getMessage());
		return ResponseEntity.badRequest().body(
				ErrorResource.builder().message(exception.getLocalizedMessage()).code(GENERIC_EXCEPTION_CODE).build());
	}

}
