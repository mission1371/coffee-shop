package ee.coffeeshop.rest.account;

import ee.coffeeshop.domain.model.Account;
import ee.coffeeshop.domain.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WithMockUser
@WebFluxTest(controllers = AccountRestController.class)
class AccountRestControllerIntegrationTest {

	@MockBean
	private AccountService service;

	@MockBean
	private AccountSettingResourceConverter converter;

	@Autowired
	private WebTestClient webClient;

	@Test
	void shouldReturnSettings() {
		// given
		when(converter.convertToResource(any())).thenCallRealMethod();
		when(service.getAccount()).thenReturn(Mono.just(Account.builder().id("1").isAdmin(true).build()));

		// when
		final WebTestClient.ResponseSpec spec = webClient.get().uri(uri -> uri.path("/accounts/settings").build())
				.exchange();

		// then
		spec.expectStatus().isOk();
		spec.expectBody().jsonPath("$.admin").isBoolean().jsonPath("$.admin").isEqualTo("true");
	}

}