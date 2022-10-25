import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./main/main.module').then((m) => m.MainModule),
  },
  {path: 'error', loadComponent: () => import('./components/error/error.component').then((c) => c.ErrorComponent)},
  {
    path: '**',
    pathMatch: 'full',
    loadComponent: () => import('./components/not-found/not-found.component').then((c) => c.NotFoundComponent)
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
