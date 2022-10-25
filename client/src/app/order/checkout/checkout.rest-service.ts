import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({providedIn: 'root'})
export class CheckoutRestService {

  constructor(private http: HttpClient) {
  }

  place(items: CartItemResource[], amount: number): Observable<void> {
    return this.http.post<void>('/api/orders', {items: items, amount: amount});
  }
}

export interface CartItemResource {
  productId: string;
  quantity: number;
}

