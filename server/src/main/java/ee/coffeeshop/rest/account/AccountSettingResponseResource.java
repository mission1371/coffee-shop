package ee.coffeeshop.rest.account;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
class AccountSettingResponseResource {

	boolean isAdmin;
}
