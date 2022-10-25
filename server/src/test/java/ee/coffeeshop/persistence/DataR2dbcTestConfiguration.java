package ee.coffeeshop.persistence;

import ee.coffeeshop.persistence.configuration.DataR2dbcConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

import static ee.coffeeshop.persistence.AbstractPersistenceIntegrationTest.DATABASE;
import static org.testcontainers.containers.PostgreSQLContainer.POSTGRESQL_PORT;

@TestConfiguration
@Import(DataR2dbcConfiguration.class)
public class DataR2dbcTestConfiguration extends AbstractR2dbcConfiguration {

	@Bean
	@Override
	public @NotNull ConnectionFactory connectionFactory() {
		return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder().host(DATABASE.getHost())
				.port(DATABASE.getMappedPort(POSTGRESQL_PORT)).database(DATABASE.getDatabaseName())
				.username(DATABASE.getUsername()).password(DATABASE.getPassword()).build());
	}

}
