import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatInputModule} from '@angular/material/input';
import {NgModule} from '@angular/core';

@NgModule({
  imports: [
    MatButtonModule,
    MatCardModule,
    MatInputModule,
  ],
  exports: [
    MatButtonModule,
    MatCardModule,
    MatInputModule,
  ],
})
export class MaterialModule { }