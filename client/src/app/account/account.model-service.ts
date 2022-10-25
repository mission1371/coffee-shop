import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AccountModelService {
  // @ts-ignore
  private accountSource: BehaviorSubject<AccountSettingsModel> = new BehaviorSubject<AccountSettingsModel>(null);

  public $account: Observable<AccountSettingsModel> = this.accountSource.asObservable();

  publish(accountSettingsModel: AccountSettingsModel): void {
    this.accountSource.next(accountSettingsModel);
  }

  get username(): string {
    return this.accountSource.value.username;
  }

}

export interface AccountSettingsModel {
  username: string;
}
