import { RouterModule, Routes } from '@angular/router';

import { CreateComponent } from './create.component';
import { NgModule } from '@angular/core';

const routes: Routes = [
  {
    path: 'create',
    component: CreateComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CreateRoutingModule { }
