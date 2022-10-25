package ee.coffeeshop.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@Testcontainers
@Import(DataR2dbcTestConfiguration.class)
@TestPropertySource(properties = "spring.liquibase.contexts=test")
public abstract class AbstractPersistenceIntegrationTest {

	@Container
	static final PostgreSQLContainer<?> DATABASE = new PostgreSQLContainer<>("postgres:15.0");

	@Autowired
	List<ReactiveCrudRepository<?, ?>> repositories;

	@DynamicPropertySource
	static void liquibaseProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.liquibase.url", DATABASE::getJdbcUrl);
		registry.add("spring.liquibase.user", DATABASE::getUsername);
		registry.add("spring.liquibase.password", DATABASE::getPassword);
	}

}
