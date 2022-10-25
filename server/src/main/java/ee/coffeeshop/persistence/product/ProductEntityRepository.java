package ee.coffeeshop.persistence.product;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductEntityRepository extends ReactiveCrudRepository<ProductEntity, Long> {

	Flux<ProductEntity> findByTypeOrderByNameAsc(final Long type);

	Flux<ProductEntity> findAllByOrderByNameAsc();

}
