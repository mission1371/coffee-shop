import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AccountRestService {
  constructor(private http: HttpClient) {
  }

  fetchAccountSettings(): Observable<AccountSettingsResource> {
    return this.http.get<AccountSettingsResource>('/api/accounts/settings');
  }
}

export interface AccountSettingsResource {
  username: string;
}
