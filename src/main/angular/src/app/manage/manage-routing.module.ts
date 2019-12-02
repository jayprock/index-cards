import { RouterModule, Routes } from '@angular/router';

import { CreateStudyGuideComponent } from './create-study-guide/create-study-guide.component';
import { EditStudyGuideComponent } from './edit-study-guide/edit-study-guide.component';
import { NgModule } from '@angular/core';
import { PrincipalResolverService } from '../core/guards/principal-resolver.service';
import { StudyGuideResolverService } from '../core/guards/study-guide-resolver.service';

const routes: Routes = [
  {
    path: 'create',
    component: CreateStudyGuideComponent,
    resolve: { principal: PrincipalResolverService }
  },
  {
    path: 'edit/:studyGuideId',
    component: EditStudyGuideComponent,
    resolve: { studyGuide: StudyGuideResolverService }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ManageRoutingModule { }
