import { CommonModule } from '@angular/common';
import { FormPanelComponent } from './form-panel/form-panel.component';
import { MaterialModule } from './material.module';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { RecaptchaComponent } from './recaptcha/recaptcha.component';

@NgModule({
  declarations: [FormPanelComponent, RecaptchaComponent],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule
  ],
  exports: [
    MaterialModule,
    ReactiveFormsModule,
    FormPanelComponent,
    RecaptchaComponent
  ]
})
export class SharedModule { }
