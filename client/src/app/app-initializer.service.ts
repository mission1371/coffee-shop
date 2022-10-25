import {Injectable} from '@angular/core';
import {Observable, tap} from 'rxjs';
import {AccountRestService} from "./account/account.rest-service";
import {AccountModelService} from "./account/account.model-service";
import {AccountModelConverter} from "./account/account.model-converter";

@Injectable({
  providedIn: 'root',
})
export class AppInitializerService {
  constructor(private accountRestService: AccountRestService, private accountService: AccountModelService) {
  }

  initialize(): Observable<any> {
    return this.accountRestService.fetchAccountSettings().pipe(tap((value) => this.accountService.publish(AccountModelConverter.convert(value))));
  }
}
