import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddCertificateComponent } from './add-certificate/add-certificate.component';
import { CertificateInfoComponent } from './certificate-info/certificate-info.component';
import { CertificatesListComponent } from './certificates-list/certificates-list.component';
import { EditCertificateComponent } from './edit-certificate/edit-certificate.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { MakeOrderComponent } from './make-order/make-order.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { OrdersListComponent } from './orders-list/orders-list.component';
import { RegistrationComponent } from './registration/registration.component';
import { TagListComponent } from './tag-list/tag-list.component';


const routes : Routes = [
  {path : '', component : CertificatesListComponent},
  {path : 'certificates', component : CertificatesListComponent},
  {path : 'login', component : LoginComponent},
  {path : 'registration', component : RegistrationComponent},
  {path : 'tags', component : TagListComponent},
  {path : 'certificate/:id', component : CertificateInfoComponent},
  {path : 'create_certificate', component : AddCertificateComponent},
  {path : 'order', component : MakeOrderComponent},
  {path : 'logout', component : LogoutComponent},
  {path : 'orders', component : OrdersListComponent},
  {path : 'edit_certificate/:id', component : EditCertificateComponent},
  {path : 'register', component : RegistrationComponent},
  {path : 'error', component : ErrorPageComponent},
  {path : 'not_found', component : NotFoundComponent}
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
