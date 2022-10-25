import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LayoutComponent} from './layout/layout.component';

const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      {
        path: 'order',
        loadChildren: () => import('../order/order.module').then((m) => m.OrderModule),
      },
      {
        path: 'inventory',
        loadChildren: () => import('../inventory/inventory.module').then((m) => m.InventoryModule),
      },
      {
        path: '',
        redirectTo: '/order',
        pathMatch: 'full',
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MainRoutingModule {
}
