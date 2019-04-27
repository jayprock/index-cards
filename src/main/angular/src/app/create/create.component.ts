import { Component, OnInit, ViewChild } from '@angular/core';

import { IndexCard } from '../core/models/index-card';
import { Router } from '@angular/router';
import { StudyGuide } from '../core/models/study-guide';
import { StudyGuideService } from '../core/services/study-guide.service';
import { NgForm } from '@angular/forms';
import { ErrorDetails } from '../core/models/error-details';

@Component({
  selector: 'idx-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit {

  @ViewChild('createForm') createForm: NgForm;

  studyGuide: StudyGuide = {
    name: "",
    description: "",
    indexCards: [
      { front: "", back: ""}
    ]
  };

  error: ErrorDetails = { serverError: false, message: null};

  constructor(private studyGuideService: StudyGuideService, private router: Router) { }

  ngOnInit() {
  }

  onSubmit() {
    if (this.createForm.form.valid) {
      this.studyGuideService.createStudyGuide(this.studyGuide).subscribe(result => {
        this.error = {};
        this.router.navigateByUrl("/" + result.studyGuideId);
      }, error => {
        this.error.serverError = true;
        this.error.message = "The request could not be completed due to a server error.";
      });
    } else {
      this.error.serverError = false;
      this.error.message = "Please enter all required fields and try again."
    }
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
