import {APP_INITIALIZER, LOCALE_ID, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {MAT_SNACK_BAR_DEFAULT_OPTIONS} from "@angular/material/snack-bar";
import {MAT_DIALOG_DEFAULT_OPTIONS} from "@angular/material/dialog";
import {AppInitializerService} from "./app-initializer.service";
import {AuthenticationInterceptor} from "./configuration/authentication.interceptor";
import {RestExceptionInterceptor} from "./configuration/rest-exception.interceptor";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [
    {provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: {duration: 2500}},
    {provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {minWidth: '70vw', maxWidth: '70vW', disableClose: true}},
    {provide: LOCALE_ID, useValue: 'en-GB'},
    {
      provide: APP_INITIALIZER,
      useFactory: (appService: AppInitializerService) => (): any => appService.initialize(),
      deps: [AppInitializerService],
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthenticationInterceptor,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: RestExceptionInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
