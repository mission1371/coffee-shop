package ee.coffeeshop.rest.order;

import ee.coffeeshop.domain.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
class OrderRestController {

	private final OrderResourceConverter resourceConverter;
	private final OrderService orderService;

	@PostMapping
	public Mono<Void> placeOrder(@Valid @RequestBody final OrderResource resource) {
		return orderService.place(resourceConverter.convert(resource));
	}

}
