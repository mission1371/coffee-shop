package ee.coffeeshop.domain.service;

import ee.coffeeshop.domain.model.Account;
import ee.coffeeshop.security.SecurityAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountService {

	private final SecurityAdapter adapter;

	public Mono<Account> getAccount() {
		return adapter.getAccount();
	}

	public Mono<String> getAccountId() {
		return adapter.getAccountId();
	}

}
