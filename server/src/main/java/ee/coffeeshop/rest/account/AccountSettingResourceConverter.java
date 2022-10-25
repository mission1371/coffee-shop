package ee.coffeeshop.rest.account;

import ee.coffeeshop.domain.model.Account;
import org.springframework.stereotype.Component;

@Component
class AccountSettingResourceConverter {

	public AccountSettingResponseResource convertToResource(final Account account) {
		return AccountSettingResponseResource.builder().isAdmin(account.isAdmin()).build();
	}

}
