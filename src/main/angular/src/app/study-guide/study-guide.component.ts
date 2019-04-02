import { Component, OnInit } from '@angular/core';
import { animate, keyframes, state, style, transition, trigger } from '@angular/animations';

import { ActivatedRoute } from '@angular/router';
import { StudyGuide } from '../core/models/study-guide';
import { StudyGuideService } from '../core/services/study-guide.service';

@Component({
  selector: 'idx-study',
  templateUrl: './study-guide.component.html',
  styleUrls: ['./study-guide.component.css'],
  animations: [
    trigger('flip', [
      state('back', style({
        transform: 'rotateY(180deg)'
      })),
      transition('front => back', [
        animate('0.8s 0s ease-out',
          keyframes([
            style({
              transform: 'perspective(2000px) rotateY(0deg)',
              offset: 0
            }),
            style({
              transform: 'perspective(2000px) scale3d(1.05, 1.05, 1.05) rotateY(80deg)',
              offset: 0.4
            }),
            style({
              transform: 'perspective(2000px) scale3d(1.06, 1.06, 1.06) rotateY(100deg)',
              offset: 0.5
            }),
            style({
              transform: 'perspective(2000px) scale3d(1, 1, 1) rotateY(180deg)',
              offset: 0.8
            }),
            style({
              transform: 'perspective(2000px) rotateY(180deg)',
              offset: 1
            })
          ])
        )
      ]),
      transition('back => front', [
        animate('0.8s 0s ease-out',
          keyframes([
            style({
              transform: 'perspective(2000px) rotateY(-180deg)',
              offset: 0
            }),
            style({
              transform: 'perspective(2000px) scale3d(1.05, 1.05, 1.05) rotateY(100deg)',
              offset: 0.4
            }),
            style({
              transform: 'perspective(2000px) scale3d(1.06, 1.06, 1.06) rotateY(80deg)',
              offset: 0.5
            }),
            style({
              transform: 'perspective(2000px) scale3d(1, 1, 1) rotateY(0deg)',
              offset: 0.8
            }),
            style({
              transform: 'perspective(2000px) rotateY(0deg)',
              offset: 1
            })
          ])
        )
      ]),
    ])
  ]
})
export class StudyGuideComponent implements OnInit {

  studyGuide: StudyGuide = {name: '', indexCards: []};
  cardNum = 0;
  visibleSide = 'front';

  constructor(private route: ActivatedRoute, private studyGuideService: StudyGuideService) { }

  ngOnInit() {
    const studyGuideId = this.route.snapshot.paramMap.get('studyGuideId');
    this.studyGuideService.findStudyGuide(studyGuideId).subscribe(studyGuide => {
      this.studyGuide = studyGuide;
    });
  }

  onIndexCardClick() {
    if (this.visibleSide == 'back') {
      this.visibleSide = 'front';
    } else {
      this.visibleSide = 'back';
    }
  }

  onPreviousClick() {
    this.cardNum--;
    if (this.cardNum < 0) {
      this.cardNum = this.studyGuide.indexCards.length - 1;
    }
    this.visibleSide = 'front';
  }

  onNextClick() {
    this.cardNum++;
    if (this.cardNum >= this.studyGuide.indexCards.length) {
      this.cardNum = 0;
    }
    this.visibleSide = 'front';
  }

}
