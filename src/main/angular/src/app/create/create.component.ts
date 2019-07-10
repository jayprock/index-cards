import { AbstractControl, FormArray, FormBuilder, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { COMMA, SEMICOLON, SPACE } from '@angular/cdk/keycodes';
import { Component, OnInit } from '@angular/core';

import { ErrorDetails } from '../core/models/error-details';
import { IndexCard } from '../core/models/index-card';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
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
  
  studyGuideForm: FormGroup;
  error: ErrorDetails = { serverError: false, message: null};

  readonly separatorKeysCodes: number[] = [ SPACE, COMMA, SEMICOLON ];

  categories = [];
  categoryOptions: string[] = ["Math", "Science", "History"];

  constructor(
    private studyGuideService: StudyGuideService, 
    private router: Router, 
    private fb: FormBuilder
    ) { }
    
    ngOnInit() {
      this.studyGuideForm = this.fb.group({
        studyGuideName: ['', Validators.required],
        categories: [this.categories],
        description: [''],
        flashCards: this.fb.array([
          this.fb.group({
            front: ['', Validators.required],
            back: ['', Validators.required]
          })
        ])
      });
      this.studyGuideForm.controls['categories'].setValue(this.categories);
  }

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

  onCategoryInputTokenEnd(event: MatChipInputEvent) {
    this.addCategory(event.value);
    event.input.value = '';
  }

  onCategorySelected(event: MatAutocompleteSelectedEvent) {
    this.addCategory(event.option.value);
  }

  private addCategory(category: string) {
    if (category && category.length > 0) {
      this.categories.push(category);
    }
  }

  removeCategory(pos: number) {
    if (this.categories[pos]) {
      this.categories.splice(pos, 1);
    }
  }

  get flashCards() {
    return this.studyGuideForm.get('flashCards') as FormArray;
  }

  
  addFlashCard() {
    this.flashCards.push(this.fb.group({ front: '', back: ''}));
    let newFormGroup = this.flashCards.at(this.flashCards.length - 1) as FormGroup;
    console.log(newFormGroup);
    newFormGroup.markAsPristine();
    newFormGroup.markAsUntouched();
    console.log(newFormGroup);
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
    this.flashCards.removeAt(pos);
  }

  onTab(pos: number) {
    if (pos == this.flashCards.length - 1) {
      this.addFlashCard();
    }
  }

}
