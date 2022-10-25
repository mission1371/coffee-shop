package ee.coffeeshop.security;

import ee.coffeeshop.domain.model.Account;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
class UserProfileConverter {
	public Account convert(final User user) {
		return Account.builder().id(user.getUsername())
				.isAdmin(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))).build();
	}
}
