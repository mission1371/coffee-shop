import {Component, Inject} from '@angular/core';
import {catchError, EMPTY, finalize, tap} from "rxjs";
import {CartModel} from "../../components/cart/cart.model-service";
import {CartItemResource, CheckoutRestService} from "./checkout.rest-service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {CheckoutDialogData} from "./checkout-dialog.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
})
export class CheckoutComponent {

  requestInProgress = false;
  amount: FormControl<number>;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: CheckoutDialogData,
    public dialogRef: MatDialogRef<CheckoutComponent>,
    private snack: MatSnackBar,
    private checkoutRestService: CheckoutRestService) {
    this.amount = new FormControl<number>(0, {
      nonNullable: true,
      validators: [Validators.required, Validators.min(data.cart.total)]
    })
  }

  increaseAmount(number: number) {
    this.amount.setValue(this.amount.value + number);
  }

  pay() {
    if (this.amount.valid) {
      this.requestInProgress = true;
      this.checkoutRestService.place(this.convert(this.data.cart), this.amount.value)
        .pipe(
          tap(value => {
            this.snack.open('Payment successful!');
            this.dialogRef.close(true);
          }),
          catchError((err, caught) => {
            this.snack.open('Payment unsuccessful!');
            return EMPTY
          }),
          finalize(() => this.requestInProgress = false)
        )
        .subscribe();
    } else {
      this.amount.markAllAsTouched()
    }
  }

  private convert(cart: CartModel): CartItemResource[] {
    return cart.items.map(value => {
      return {productId: value.product.id, quantity: value.quantity}
    });
  }
}
