import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({providedIn: 'root'})
export class ProductRestService {

  constructor(private http: HttpClient) {
  }

  fetchConsumables(): Observable<ProductResource[]> {
    return this.fetchByProductType(1);
  }

  fetchDonatables(): Observable<ProductResource[]> {
    return this.fetchByProductType(2);
  }

  fetch(): Observable<ProductResource[]> {
    return this.http.get<ProductResource[]>('/api/products');
  }

  update(productId: string, stock: number): Observable<ProductResource> {
    return this.http.put<ProductResource>(`/api/products/${productId}`, {stock: stock});
  }

  private fetchByProductType(type: number): Observable<ProductResource[]> {
    const parameters = new HttpParams().set('type', String(type));
    return this.http.get<ProductResource[]>('/api/products', {params: parameters});
  }

}

export interface ProductResource {
  id: string;
  name: string;
  type: number;
  price: number;
  stock: number,
  currencyCode: string;
  imageUrl: string,
}
