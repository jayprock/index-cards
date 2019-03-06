import { RouterModule, Routes } from '@angular/router';

import { NgModule } from '@angular/core';
import { StudyGuideComponent } from './study-guide.component';

const routes: Routes = [
  {
    path: ':studyGuideId',
    component: StudyGuideComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class StudyGuideRoutingModule { }
