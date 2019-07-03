import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Component } from '@angular/core';
import { ErrorDetails } from '../core/models/error-details';
import { IndexCard } from '../core/models/index-card';
import { Router } from '@angular/router';
import { StudyGuide } from '../core/models/study-guide';
import { StudyGuideService } from '../core/services/study-guide.service';

@Component({
  selector: 'idx-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent {
  
  studyGuideForm = this.fb.group({
    studyGuideName: ['', Validators.required],
    description: [''],
    flashCards: this.fb.array([
      this.fb.group({
        front: ['', Validators.required],
        back: ['', Validators.required]
      })
    ])
  });

  error: ErrorDetails = { serverError: false, message: null};

  constructor(
    private studyGuideService: StudyGuideService, 
    private router: Router, 
    private fb: FormBuilder
    ) { }

  onSubmit() {
    if (this.studyGuideForm.valid) {
      this.studyGuideService.createStudyGuide(this.constructStudyGuide()).subscribe(result => {
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
  
  private constructFlashCards(): IndexCard[] {
    let flashCards: IndexCard[] = [];
    this.flashCards.controls.forEach(control => {
      let fg = control as FormGroup;
      flashCards.push({ front: fg.get('front').value, back: fg.get('back').value });
    });
    return flashCards;
  }
  
  private constructStudyGuide(): StudyGuide {
    let studyGuide: StudyGuide = {
      studyGuideName: this.studyGuideForm.get('studyGuideName').value,
      description: this.studyGuideForm.get('description').value,
      categories: [],
      flashCards: this.constructFlashCards()
    };
    return studyGuide;
  }

  isRemovable() {
    return this.flashCards.length > 1;
  }

  removeFlashCard(pos: number) {
    this.flashCards.controls.splice(pos, 1);
  }

  onTab(pos: number) {
    if (pos == this.flashCards.length - 1) {
      this.addFlashCard();
    }
  }

}
