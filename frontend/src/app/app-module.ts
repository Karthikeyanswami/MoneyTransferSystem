import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule, provideClientHydration, withEventReplay } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Login } from './components/login/login';
import { Dashboard } from './components/dashboard/dashboard';
import {  HTTP_INTERCEPTORS, provideHttpClient, withFetch, withInterceptors, withInterceptorsFromDi } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { History } from './components/history/history';
import { CommonModule } from '@angular/common';
import { Transfer } from './components/transfer/transfer';
import { Profile } from './components/profile/profile';
import { HttpInterceptorService } from './core/services/http-interceptor-service';



@NgModule({
  declarations: [
    App,
    Login,
    Dashboard,
    History,
    Transfer,
    Profile
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule
    

  ],
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideHttpClient(
      withFetch(),
      withInterceptors([HttpInterceptorService]),
    ),
    provideClientHydration(withEventReplay()),
    
    provideHttpClient(withFetch())
  ],
  bootstrap: [App]
})
export class AppModule { }
