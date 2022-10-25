package ee.coffeeshop.rest.account;

import ee.coffeeshop.domain.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
class AccountRestController {

	private final AccountService accountService;

	private final AccountSettingResourceConverter converter;

	@GetMapping("/settings")
	public Mono<AccountSettingResponseResource> getSettings() {
		return accountService.getAccount().map(converter::convertToResource);
	}

}
