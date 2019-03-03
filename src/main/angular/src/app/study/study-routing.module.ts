import { RouterModule, Routes } from '@angular/router';

import { NgModule } from '@angular/core';
import { StudyComponent } from './study.component';

const routes: Routes = [
  {
    path: ':studyId',
    component: StudyComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class StudyRoutingModule { }
