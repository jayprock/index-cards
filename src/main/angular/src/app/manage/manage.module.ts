import { CommonModule } from '@angular/common';
import { ManageRoutingModule } from './manage-routing.module';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { StudyGuideFormComponent } from './study-guide-form.component';
import { CreateStudyGuideComponent } from './create-study-guide/create-study-guide.component';

@NgModule({
  declarations: [StudyGuideFormComponent, CreateStudyGuideComponent],
  imports: [
    CommonModule,
    ManageRoutingModule,
    SharedModule,
  ]
})
export class ManageModule { }
