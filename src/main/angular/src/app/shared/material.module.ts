import {
  MatButtonModule,
  MatCardModule,
} from '@angular/material';

import {NgModule} from '@angular/core';

@NgModule({
  imports: [
    MatButtonModule,
    MatCardModule,
  ],
  exports: [
    MatButtonModule,
    MatCardModule,
  ],
})
export class MaterialModule { }