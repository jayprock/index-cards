import { Component, OnInit } from '@angular/core';

import { IndexCard } from '../core/models/index-card';

@Component({
  selector: 'idx-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit {

  flashCards: IndexCard[] = [
    { front: "", back: "" }
  ];

  constructor() { }

  ngOnInit() {
  }

  addFlashCard() {
    this.flashCards.push({ front: "", back: "" });
    console.log("length: " + this.flashCards.length);
  }
}
