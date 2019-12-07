import { CommonModule } from '@angular/common';
import { CreateStudyGuideComponent } from './create-study-guide/create-study-guide.component';
import { EditStudyGuideComponent } from './edit-study-guide/edit-study-guide.component';
import { ManageRoutingModule } from './manage-routing.module';
import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { StudyGuideFormComponent } from './study-guide-form.component';

@NgModule({
  declarations: [StudyGuideFormComponent, CreateStudyGuideComponent, EditStudyGuideComponent],
  imports: [
    CommonModule,
    ManageRoutingModule,
    SharedModule,
  ]
})
export class ManageModule { }
