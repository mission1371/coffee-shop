package ee.coffeeshop.domain.service;

import ee.coffeeshop.domain.exception.ProductNotFoundException;
import ee.coffeeshop.domain.exception.ProductOutOfStockException;
import ee.coffeeshop.domain.model.Product;
import ee.coffeeshop.persistence.product.ProductPersistenceAdaptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Service
@Validated
@RequiredArgsConstructor
public class ProductService {

	private final ProductPersistenceAdaptor adaptor;

	public Flux<Product> getByType(@NotNull final Long type) {
		return adaptor.getByType(type);
	}

	public Mono<Product> getById(@NotNull final Long productId) {
		return adaptor.getById(productId).switchIfEmpty(Mono.error(ProductNotFoundException::new));
	}

	public Mono<Product> reduce(@NotNull final Long productId, @Min(1) final int quantity) {
		return getById(productId).flatMap(product -> adaptor.reduce(product, quantity)).flatMap(product -> {
			if (product.getStock() < 0) {
				return Mono.error(ProductOutOfStockException::new);
			} else {
				return Mono.just(product);
			}
		});
	}

	public Mono<Product> increase(@NotNull final Long productId, @Min(1) final int quantity) {
		return getById(productId).flatMap(product -> adaptor.increase(product, quantity));
	}

	public Flux<Product> findAll() {
		return adaptor.findAll();
	}
}
