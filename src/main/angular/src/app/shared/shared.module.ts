import { CommonModule } from '@angular/common';
import { FormPanelComponent } from './form-panel/form-panel.component';
import { MaterialModule } from './material.module';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [FormPanelComponent],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule
  ],
  exports: [
    MaterialModule,
    ReactiveFormsModule,
    FormPanelComponent
  ]
})
export class SharedModule { }
