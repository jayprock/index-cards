import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { StudyGuideComponent } from './study-guide.component';
import { StudyGuideRoutingModule } from './study-guide-routing.module';

@NgModule({
  declarations: [StudyGuideComponent],
  imports: [
    CommonModule,
    StudyGuideRoutingModule
  ]
})
export class StudyGuideModule { }
