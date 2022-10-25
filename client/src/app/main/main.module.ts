import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {MainRoutingModule} from './main-routing.module';
import {LayoutComponent} from './layout/layout.component';
import {HeaderComponent} from './header/header.component';
import {AppMaterialModule} from "../app-material.module";

@NgModule({
  declarations: [LayoutComponent, HeaderComponent],
  imports: [CommonModule, MainRoutingModule, AppMaterialModule],
  exports: [AppMaterialModule],
})
export class MainModule {
}
