package ee.coffeeshop.persistence.configuration;

import ee.coffeeshop.domain.service.AccountService;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.reactive.TransactionalOperator;

@Configuration
@EnableR2dbcAuditing
@EnableR2dbcRepositories
@EnableTransactionManagement
public class DataR2dbcConfiguration {

	@Bean
	ReactiveAuditorAware<String> auditorAware(final AccountService service) {
		return service::getAccountId;
	}

	@Bean
	TransactionalOperator transactionalOperator(final ReactiveTransactionManager transactionManager) {
		return TransactionalOperator.create(transactionManager);
	}

	@Bean
	ReactiveTransactionManager transactionManager(final ConnectionFactory connectionFactory) {
		return new R2dbcTransactionManager(connectionFactory);
	}
}
