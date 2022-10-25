package ee.coffeeshop.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

class UserProfile extends User {

	public UserProfile(final String username, final String password, final Collection<String> authorities) {
		super(username, password, authorities.stream().map(SimpleGrantedAuthority::new).toList());
	}

}
