import {Component, OnInit} from '@angular/core';
import {ProductModel} from "../components/product/product.model";
import {CartModelService} from "../components/cart/cart.model-service";
import {finalize, map, merge, tap} from "rxjs";
import {ProductResource, ProductRestService} from "../components/product/product-rest.service";
import {CheckoutDialogService} from "./checkout/checkout-dialog.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html'
})
export class OrderComponent implements OnInit {

  consumables!: ProductModel[];
  donatables!: ProductModel[];
  requestInProgress = false;

  constructor(
    private snack: MatSnackBar,
    private checkoutDialogService: CheckoutDialogService,
    private productRestService: ProductRestService,
    private cartModelService: CartModelService) {
  }

  ngOnInit(): void {
    this.loadProducts();
  }

  onSelect(product: ProductModel) {
    this.cartModelService.add(product);
  }

  onRemove($event: ProductModel) {
    this.cartModelService.remove($event);
  }

  onRemoveAll($event: ProductModel) {
    this.cartModelService.discard($event);
  }

  emptyBasket() {
    if (!this.cartModelService.isEmpty()) {
      this.cartModelService.empty();
      this.loadProducts();
    }
  }

  checkout() {
    if (!this.cartModelService.isEmpty()) {
      let subscription = this.checkoutDialogService.open(this.cartModelService.getCart()).afterClosed().subscribe(value => {
        if (value) {
          this.emptyBasket();
          subscription.unsubscribe();
        }
      });
    } else {
      this.snack.open('Please select product first.');
    }
  }

  private loadProducts() {
    this.requestInProgress = true;
    merge(
      this.productRestService.fetchDonatables()
        .pipe(
          map(values => this.convert(values)),
          tap(value => this.donatables = value)
        ),
      this.productRestService.fetchConsumables()
        .pipe(
          map(values => this.convert(values)),
          tap(value => this.consumables = value)
        )
    )
      .pipe(finalize(() => this.requestInProgress = false))
      .subscribe();
  }

  private convert(values: ProductResource[]): ProductModel[] {
    return values.map(value => {
      return {
        id: value.id,
        name: value.name,
        type: value.type,
        src: value.imageUrl,
        stock: value.stock,
        price: value.price,
        currencyCode: value.currencyCode,
      }
    });
  }

}
