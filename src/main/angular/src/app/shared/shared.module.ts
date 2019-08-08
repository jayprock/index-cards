import { CommonModule } from '@angular/common';
import { MaterialModule } from './material.module';
import { NgModule } from '@angular/core';
import { FormPanelComponent } from './form-panel/form-panel.component';

@NgModule({
  declarations: [FormPanelComponent],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [
    MaterialModule,
    FormPanelComponent
  ]
})
export class SharedModule { }
