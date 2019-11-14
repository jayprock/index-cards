import { RouterModule, Routes } from '@angular/router';

import { CreateComponent } from './create.component';
import { NgModule } from '@angular/core';
import { PrincipalResolverService } from '../core/guards/principal-resolver.service';

const routes: Routes = [
  {
    path: 'create',
    component: CreateComponent,
    resolve: { principal: PrincipalResolverService }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CreateRoutingModule { }
