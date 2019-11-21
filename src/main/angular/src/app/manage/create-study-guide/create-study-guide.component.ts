import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';

import { Principal } from '../../core/models/principal';
import { StudyGuide } from '../../core/models/study-guide';
import { StudyGuideService } from '../../core/services/study-guide.service';

@Component({
  selector: 'idx-create-study-guide',
  templateUrl: './create-study-guide.component.html',
  styleUrls: ['./create-study-guide.component.css']
})
export class CreateStudyGuideComponent implements OnInit {

  serverError: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private studyGuideService: StudyGuideService
  ) { }

  ngOnInit() {
    this.route.data
      .subscribe((data: { principal: Principal }) => {
        if (data.principal == null) {
          window.alert("TODO - Design authorized users only notification");
        }
      });
  }

  onSubmit(studyGuide: StudyGuide) {
    this.serverError = null;
    this.studyGuideService.createStudyGuide(studyGuide)
      .subscribe(
        result => {
          this.router.navigateByUrl("/" + result.studyGuideId);
      }, error => {
        this.serverError = "The request could not be completed due to a server error";
      });
  }

}
