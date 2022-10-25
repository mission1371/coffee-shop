import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ProductModel} from "./product.model";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import {MatBadgeModule} from "@angular/material/badge";
import {CommonModule} from "@angular/common";

@Component({
  standalone: true,
  imports: [CommonModule, MatIconModule, MatButtonModule, MatCardModule, MatBadgeModule],
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent {

  selectedAmount: number = 0;

  @Input()
  product!: ProductModel;

  @Output()
  removed: EventEmitter<ProductModel> = new EventEmitter<ProductModel>();

  @Output()
  selected: EventEmitter<ProductModel> = new EventEmitter<ProductModel>();

  @Output()
  unselectAll: EventEmitter<ProductModel> = new EventEmitter<ProductModel>();

  get outOfStock(): boolean {
    return this.product.stock === this.selectedAmount;
  };

  unselectProduct(): void {
    if (this.selectedAmount !== 0) {
      this.removed.emit(this.product);
      this.selectedAmount--;
    }
  }

  unselectAllProducts(): void {
    this.unselectAll.emit(this.product);
    this.selectedAmount = 0;
  }

  selectProduct(): void {
    if (!this.outOfStock) {
      this.selected.emit(this.product);
      this.selectedAmount++;
    }
  }

}
