import { COMMA, SEMICOLON, SPACE } from '@angular/cdk/keycodes';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, NgForm, Validators } from '@angular/forms';

import { ErrorDetails } from '../core/models/error-details';
import { IndexCard } from '../core/models/index-card';
import { MatChipInputEvent } from '@angular/material/chips';
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
    studyGuideName: "",
    description: "",
    categories: [],
    flashCards: [
      { front: "", back: ""}
    ]
  };

  error: ErrorDetails = { serverError: false, message: null};

  readonly separatorKeysCodes: number[] = [ SPACE, COMMA, SEMICOLON ];

  studyGuideForm = this.fb.group({
    studyGuideName: ['', Validators.required],
    description: [''],
    flashCards: this.fb.array([
      this.fb.group({
        front: [],
        back: []
      })
    ])
  });

  constructor(private studyGuideService: StudyGuideService, private router: Router, private fb: FormBuilder) { }

  ngOnInit() {
  }

  onSubmit() {
    this.triggerValidation();
    if (this.studyGuideForm.valid) {
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

  get flashCards() {
    return this.studyGuideForm.get('flashCards') as FormArray;
  }

  addFlashCard() {
    this.flashCards.push(this.fb.group({ front: '', back: ''}));
  }

  isRemovable() {
    return this.studyGuide.flashCards.length > 1;
  }

  removeFlashCard(pos: number) {
    this.studyGuide.flashCards.splice(pos, 1);
  }

  onTab(pos: number) {
    if (pos == this.studyGuide.flashCards.length - 1) {
      this.addFlashCard();
    }
  }

  private triggerValidation() {
    // for (var fieldName in this.createForm.controls) {
    //   this.createForm.controls[fieldName].markAsTouched();
    // }
  }

}
