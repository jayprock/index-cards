import { Component, OnInit } from '@angular/core';

import { ActivatedRoute } from '@angular/router';
import { StudyGuide } from '../core/models/study-guide';
import { StudyGuideService } from '../core/services/study-guide.service';

@Component({
  selector: 'idx-study',
  templateUrl: './study-guide.component.html',
  styleUrls: ['./study-guide.component.css']
})
export class StudyGuideComponent implements OnInit {

  studyGuide: StudyGuide = {name: '', indexCards: []};

  constructor(private route: ActivatedRoute, private studyGuideService: StudyGuideService) { }

  ngOnInit() {
    const studyGuideId = this.route.snapshot.paramMap.get('studyGuideId');
    this.studyGuideService.findStudyGuide(studyGuideId)
      .subscribe(studyGuide => this.studyGuide = studyGuide);
  }

}
