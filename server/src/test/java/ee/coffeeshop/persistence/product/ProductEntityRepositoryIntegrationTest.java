package ee.coffeeshop.persistence.product;

import ee.coffeeshop.domain.service.AccountService;
import ee.coffeeshop.persistence.AbstractPersistenceIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static ee.coffeeshop.persistence.product.ProductEntityBuilder.muffin;
import static ee.coffeeshop.persistence.product.ProductEntityBuilder.shirt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DataR2dbcTest
class ProductEntityRepositoryIntegrationTest extends AbstractPersistenceIntegrationTest {

	@MockBean
	private AccountService accountService;

	@Autowired
	private ProductEntityRepository repository;

	@BeforeEach
	void setup() {
		when(accountService.getAccountId()).thenReturn(Mono.just("1"));
		StepVerifier.create(repository.deleteAll()).verifyComplete();
	}

	@Test
	void shouldFindEatableProducts() {
		// given
		final ProductEntity muffin = muffin();
		final ProductEntity shirt = shirt();
		insertProducts(muffin, shirt);

		// when
		final Flux<ProductEntity> result = repository.findByTypeOrderByNameAsc(1L);

		// then
		result.as(StepVerifier::create).assertNext(entity -> {
			assertThat(entity).usingRecursiveComparison()
					// TODO fractional digits on nanoseconds are not truncated to 6 from 9 with
					// java17. Hibernate v6 will fix it. Then remove this ignores.
					// https://discourse.hibernate.org/t/java-17-version-and-nanoseconds-truncation/6128
					.ignoringFields("createdAt", "modifiedAt").isEqualTo(muffin);
		}).verifyComplete();

	}

	@Test
	void shouldFindDonatableProducts() {
		// given
		final ProductEntity muffin = muffin();
		final ProductEntity shirt = shirt();
		insertProducts(muffin, shirt);

		// when
		final Flux<ProductEntity> result = repository.findByTypeOrderByNameAsc(2L);

		// then
		result.as(StepVerifier::create).assertNext(entity -> {
			assertThat(entity).usingRecursiveComparison().ignoringFields("createdAt", "modifiedAt").isEqualTo(shirt);
		}).verifyComplete();
	}

	private void insertProducts(final ProductEntity... entities) {
		this.repository.saveAll(Arrays.asList(entities)).as(StepVerifier::create).expectNextCount(entities.length)
				.verifyComplete();
	}
}