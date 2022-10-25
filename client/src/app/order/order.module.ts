import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {OrderRoutingModule} from './order-routing.module';
import {OrderComponent} from './order.component';
import {ProductComponent} from "../components/product/product.component";
import {CartComponent} from "../components/cart/cart.component";
import {OverlayComponent} from "../components/overlay/overlay.component";
import {AppMaterialModule} from "../app-material.module";
import {CheckoutComponent} from "./checkout/checkout.component";


@NgModule({
  declarations: [
    OrderComponent,
    CheckoutComponent
  ],
  imports: [
    CommonModule,
    OrderRoutingModule,
    AppMaterialModule,
    ProductComponent,
    CartComponent,
    OverlayComponent
  ]
})
export class OrderModule {
}
