import { Component, OnInit } from '@angular/core';

import { IndexCard } from '../core/models/index-card';
import { StudyGuide } from '../core/models/study-guide';

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

  constructor() { }

  ngOnInit() {
  }

  addFlashCard() {
    this.studyGuide.indexCards.push({ front: "", back: "" });
  }
}
