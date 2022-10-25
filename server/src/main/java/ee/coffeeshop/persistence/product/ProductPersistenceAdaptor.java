package ee.coffeeshop.persistence.product;

import ee.coffeeshop.domain.exception.ProductOutOfStockException;
import ee.coffeeshop.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

@Service
@Validated
@RequiredArgsConstructor
public class ProductPersistenceAdaptor {
	private final ProductEntityRepository repository;
	private final ProductEntityConverter converter;

	public Flux<Product> findAll() {
		return repository.findAllByOrderByNameAsc().map(converter::convert);
	}

	public Flux<Product> getByType(@NotNull final Long type) {
		return repository.findByTypeOrderByNameAsc(type).map(converter::convert);
	}

	public Mono<Product> getById(@NotNull final Long productId) {
		return repository.findById(productId).map(converter::convert);
	}

	public Mono<Product> reduce(@NotNull final Product product, final int quantity) {
		return repository.findById(product.getId()).flatMap(entity -> updateStock(entity, Math.negateExact(quantity)))
				.flatMap(repository::save).map(converter::convert);
	}

	public Mono<Product> increase(@NotNull final Product product, final int quantity) {
		return repository.findById(product.getId()).flatMap(entity -> updateStock(entity, quantity))
				.flatMap(repository::save).map(converter::convert);
	}

	private static Mono<ProductEntity> updateStock(final ProductEntity entity, final int quantity) {
		entity.setStock(entity.getStock() + quantity);
		if (entity.getStock() < 0) {
			throw new ProductOutOfStockException();
		}
		return Mono.just(entity);
	}

}
