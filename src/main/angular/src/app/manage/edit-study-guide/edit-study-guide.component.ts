import { Component, OnInit } from '@angular/core';

import { ActivatedRoute } from '@angular/router';
import { StudyGuide } from 'src/app/core/models/study-guide';

@Component({
  selector: 'idx-edit-study-guide',
  templateUrl: './edit-study-guide.component.html',
  styleUrls: ['./edit-study-guide.component.css']
})
export class EditStudyGuideComponent implements OnInit {

  studyGuide: StudyGuide;
  constructor(
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.route.data
      .subscribe((data: { studyGuide: StudyGuide }) => {
        this.studyGuide = data.studyGuide;
      });
  }

}
