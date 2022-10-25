import {AccountSettingsResource} from "./account.rest-service";
import {AccountSettingsModel} from "./account.model-service";

export class AccountModelConverter {
  static convert(accountSettingResource: AccountSettingsResource): AccountSettingsModel {
    return {
      username: accountSettingResource.username,
    };
  }
}
