import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CartItemModel, CartModelService} from "./cart.model-service";
import {tap} from "rxjs";
import {MatDividerModule} from "@angular/material/divider";


@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, MatDividerModule],
  templateUrl: './cart.component.html'
})
export class CartComponent implements OnInit {

  items!: CartItemModel[];
  cartTotal!: number;
  cartCurrencyCode!: string;

  constructor(private service: CartModelService) {
  }

  ngOnInit(): void {
    this.service.$cartChanged.pipe(
      tap(value => {
        this.items = value.items;
        this.cartTotal = value.total;
        this.cartCurrencyCode = value.currencyCode;
      })
    ).subscribe()
  }

}

