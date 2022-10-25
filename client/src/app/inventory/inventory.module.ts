import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {InventoryComponent} from './inventory.component';
import {AppMaterialModule} from "../app-material.module";
import {OverlayComponent} from "../components/overlay/overlay.component";
import {RouterModule, Routes} from "@angular/router";

const routes: Routes = [{path: '', component: InventoryComponent}];

@NgModule({
  declarations: [
    InventoryComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    AppMaterialModule,
    OverlayComponent
  ],
  exports: [RouterModule]
})
export class InventoryModule {
}
