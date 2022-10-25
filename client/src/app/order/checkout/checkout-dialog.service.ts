import {Injectable} from '@angular/core';
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {CartModel} from "../../components/cart/cart.model-service";
import {CheckoutComponent} from "./checkout.component";

@Injectable({
  providedIn: 'any'
})
export class CheckoutDialogService {

  constructor(private dialog: MatDialog) {
  }

  open(cart: CartModel): MatDialogRef<CheckoutComponent, boolean> {
    const data: CheckoutDialogData = {cart};
    return this.dialog.open<CheckoutComponent, CheckoutDialogData>(CheckoutComponent, {data});
  }
}

export interface CheckoutDialogData {
  cart: CartModel;
}
