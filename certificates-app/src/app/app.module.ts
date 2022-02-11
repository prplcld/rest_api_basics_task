import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Component, ErrorHandler, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CertificatesListComponent } from './certificates-list/certificates-list.component';
import { NavbarComponent } from './navbar/navbar.component';
import { CertificateInfoComponent } from './certificate-info/certificate-info.component';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { AddCertificateComponent } from './add-certificate/add-certificate.component';
import { TagListComponent } from './tag-list/tag-list.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MakeOrderComponent } from './make-order/make-order.component';
import { EditCertificateComponent } from './edit-certificate/edit-certificate.component';
import { LogoutComponent } from './logout/logout.component';
import { AuthInerceptor } from './shared/interceptors/auth.interceptor';
import { ErrorCatchingInterceptor } from './shared/interceptors/error.interceptor';
import { GlobalErrorHandler } from './shared/error-handler/error.handler';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatPaginatorModule} from '@angular/material/paginator';
import { OrdersListComponent } from './orders-list/orders-list.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { ErrorPageComponent } from './error-page/error-page.component'


@NgModule({
  declarations: [
    AppComponent,
    CertificatesListComponent,
    NavbarComponent,
    CertificateInfoComponent,
    LoginComponent,
    RegistrationComponent,
    AddCertificateComponent,
    TagListComponent,
    MakeOrderComponent,
    EditCertificateComponent,
    LogoutComponent,
    OrdersListComponent,
    NotFoundComponent,
    ErrorPageComponent
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatPaginatorModule
  ],
  providers: [{
    provide : HTTP_INTERCEPTORS,
    useClass : AuthInerceptor,
    multi : true
  },
  {
    provide: HTTP_INTERCEPTORS,
    useClass: ErrorCatchingInterceptor,
    multi: true
},
{provide: ErrorHandler, useClass: GlobalErrorHandler}],
  bootstrap: [AppComponent]
})
export class AppModule { }
