package ee.coffeeshop.domain.service;

import ee.coffeeshop.domain.exception.InsufficientAmountException;
import ee.coffeeshop.domain.model.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Service
@Validated
@RequiredArgsConstructor
public class OrderService {

	private final ProductService productService;

	public Mono<Void> place(@NotNull final OrderRequest request) {

		return Flux.fromIterable(request.getItems())
				.flatMap(item -> Mono.zip(productService.reduce(item.getProductId(), item.getQuantity()),
						Mono.just(item)))
				.flatMap(productWithItem -> Mono.just(productWithItem.getT1().getPrice()
						.multiply(BigDecimal.valueOf(productWithItem.getT2().getQuantity()))))
				.reduce(BigDecimal.ZERO, BigDecimal::add).flatMap(total -> {
					final BigDecimal exchange = request.getAmount().subtract(total);
					if (exchange.compareTo(BigDecimal.ZERO) < 0) {
						return Mono.error(new InsufficientAmountException());
					} else {
						return Mono.empty();
					}
				});

	}
}
