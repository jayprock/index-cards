import { RouterModule, Routes } from '@angular/router';

import { CreateStudyGuideComponent } from './create-study-guide/create-study-guide.component';
import { NgModule } from '@angular/core';
import { PrincipalResolverService } from '../core/guards/principal-resolver.service';
import { StudyGuideFormComponent } from './study-guide-form.component';

const routes: Routes = [
  {
    path: 'create',
    component: CreateStudyGuideComponent,
    resolve: { principal: PrincipalResolverService }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ManageRoutingModule { }
