package ee.coffeeshop.security;

import ee.coffeeshop.domain.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SecurityAdapter {

	private final UserProfileConverter converter;

	public Mono<Account> getAccount() {
		return ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication)
				.map(Authentication::getPrincipal).cast(User.class).map(converter::convert);
	}

	public Mono<String> getAccountId() {
		return getAccount().map(Account::getId);
	}

}
