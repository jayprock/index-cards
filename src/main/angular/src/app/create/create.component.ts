import { Component, OnInit } from '@angular/core';

import { IndexCard } from '../core/models/index-card';
import { Router } from '@angular/router';
import { StudyGuide } from '../core/models/study-guide';
import { StudyGuideService } from '../core/services/study-guide.service';

@Component({
  selector: 'idx-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit {

  studyGuide: StudyGuide = {
    name: "",
    description: "",
    indexCards: [
      { front: "", back: ""}
    ]
  };

  constructor(private studyGuideService: StudyGuideService, private router: Router) { }

  ngOnInit() {
  }

  onSubmit() {
    this.studyGuideService.createStudyGuide(this.studyGuide).subscribe(result => {
      this.router.navigateByUrl("/" + result.studyGuideId);
    })
  }

  addFlashCard() {
    this.studyGuide.indexCards.push({ front: "", back: "" });
  }

  isRemovable() {
    return this.studyGuide.indexCards.length > 1;
  }

  removeFlashCard(pos: number) {
    this.studyGuide.indexCards.splice(pos, 1);
  }

  onTab(pos: number) {
    if (pos == this.studyGuide.indexCards.length - 1) {
      this.addFlashCard();
    }
  }
}
