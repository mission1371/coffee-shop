import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTable} from "@angular/material/table";
import {MatSnackBar} from "@angular/material/snack-bar";
import {catchError, EMPTY, finalize, map, tap} from "rxjs";
import {ProductResource, ProductRestService} from "../components/product/product-rest.service";

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html'
})
export class InventoryComponent implements OnInit {

  @ViewChild(MatTable)
  table!: MatTable<InventoryData>;

  inventory!: InventoryData[];
  displayedColumns: string[] = ['number', 'name', 'stock', 'actions'];
  requestInProgress = false;

  constructor(private rest: ProductRestService, public snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.requestInProgress = true;
    this.rest.fetch().pipe(
      map(values => values.map(value => this.convert(value))),
      tap(data => this.inventory = data),
      finalize(() => this.requestInProgress = false)
    )
      .subscribe()

  }

  increaseStock(product: InventoryData, stock: number, index: number): void {
    this.requestInProgress = true;
    this.rest.update(product.id, stock)
      .pipe(
        tap(value => {
          this.inventory[index] = this.convert(value);
          this.table.renderRows();
          this.snackBar.open(`Stock updated`);
        }),
        catchError(() => {
          this.snackBar.open('Failed to update stock. Please try again later.');
          return EMPTY;
        }),
        finalize(() => this.requestInProgress = false)
      )
      .subscribe()
  }

  private convert(resource: ProductResource): InventoryData {
    return {
      id: resource.id,
      name: resource.name,
      stock: resource.stock,
      showEditStock: false,
      showLoader: false
    };
  }
}

interface InventoryData {
  id: string;
  name: string;
  stock: number;
  showEditStock: boolean;
  showLoader: boolean;
}
