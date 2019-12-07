import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';

import { StudyGuide } from 'src/app/core/models/study-guide';
import { StudyGuideService } from '../../core/services/study-guide.service';

@Component({
  selector: 'idx-edit-study-guide',
  templateUrl: './edit-study-guide.component.html',
  styleUrls: ['./edit-study-guide.component.css']
})
export class EditStudyGuideComponent implements OnInit {

  serverError: string;

  studyGuide: StudyGuide;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private studyGuideService: StudyGuideService
  ) { }

  ngOnInit() {
    this.route.data
      .subscribe((data: { studyGuide: StudyGuide }) => {
        this.studyGuide = data.studyGuide;
      });
  }

  onSubmit(studyGuide: StudyGuide) {
    this.serverError = null;
    this.studyGuideService.updateStudyGuide(studyGuide)
      .subscribe(
        result => {
          this.router.navigateByUrl("/" + result.studyGuideId);
      }, error => {
        this.serverError = "The request could not be completed due to a server error";
      });
  }

}
