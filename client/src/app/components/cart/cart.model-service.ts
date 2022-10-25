import {Injectable} from '@angular/core';
import {ProductModel} from "../product/product.model";
import {BehaviorSubject, Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class CartModelService {

  private EMPTY_CART: () => CartModel = () => ({items: [], total: 0, currencyCode: ''});

  private source: BehaviorSubject<CartModel> = new BehaviorSubject<CartModel>(this.EMPTY_CART());
  public $cartChanged: Observable<CartModel> = this.source.asObservable();

  constructor() {
  }

  isEmpty(): boolean {
    return this.source.getValue().items.length === 0;
  }

  empty() {
    this.source.next(this.EMPTY_CART())
  }

  discard(model: ProductModel) {
    const items = this.source.getValue().items;
    let index = -1;
    let total = 0;
    items.forEach((value, i) => {
      if (value.product.id === model.id) {
        index = i;
      } else {
        total = total + value.totalPrice;
      }
    });
    if (index !== -1) {
      items.splice(index, 1);
    }
    this.source.next({items: items, total: total, currencyCode: items[0]?.product.currencyCode});
  }

  remove(model: ProductModel) {
    const items = this.source.getValue().items;
    let index = -1;
    let total = 0;
    items.forEach((value, i) => {
      if (value.product.id === model.id) {
        index = i;
      }
      total = total + value.totalPrice;
    });
    if (index !== -1) {
      const item: CartItemModel = items[index];
      item.quantity = item.quantity - 1;
      item.totalPrice = item.quantity * item.product.price;
      total = total - item.product.price;
      if (item.quantity < 1) {
        items.splice(index, 1);
      }
    }

    this.source.next({items: items, total: total, currencyCode: items[0]?.product.currencyCode});
  }

  add(model: ProductModel) {
    const items = this.source.getValue().items;
    let index = -1;
    let total = 0;
    items.forEach((value, i) => {
      if (value.product.id === model.id) {
        index = i;
      } else {
        total = total + value.totalPrice;
      }
    });
    if (index == -1) {
      items.push({product: model, quantity: 0, totalPrice: 0});
      index = items.length - 1;
    }
    const item: CartItemModel = items[index];
    item.quantity = item.quantity + 1;
    item.totalPrice = item.quantity * item.product.price;
    total = total + item.totalPrice;

    this.source.next({items: items, total: total, currencyCode: items[0].product.currencyCode});
  }

  getCart(): CartModel {
    return this.source.getValue();
  }

}

export interface CartModel {
  currencyCode: string;
  total: number;
  items: CartItemModel[]
}

export interface CartItemModel {
  quantity: number;
  product: ProductModel;
  totalPrice: number;
}
