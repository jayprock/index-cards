import { RouterModule, Routes } from '@angular/router';

import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { LoginComponent } from './login.component';
import { NgModule } from '@angular/core';
import { PasswordResetEmailComponent } from './password-reset-email/password-reset-email.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'password-reset-email', component: PasswordResetEmailComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LoginRoutingModule { }
