import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { COMMA, SEMICOLON, SPACE } from '@angular/cdk/keycodes';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatAutocomplete, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { debounceTime, filter, map, startWith, switchMap, tap } from 'rxjs/operators';

import { ErrorDetails } from '../core/models/error-details';
import { IndexCard } from '../core/models/index-card';
import { MatChipInputEvent } from '@angular/material/chips';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { StudyGuide } from '../core/models/study-guide';
import { StudyGuideCategoryService } from '../core/services/study-guide-category.service';
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
  categoryInputCtrl = new FormControl();
  categoryNames = [];
  categoriesFound: Observable<string[]>;

  @ViewChild("categoryInputEl") categoryInputEl: ElementRef<HTMLInputElement>;
  @ViewChild("auto") autoComplete: MatAutocomplete;

  constructor(
    private studyGuideService: StudyGuideService, 
    private categoryService: StudyGuideCategoryService,
    private router: Router, 
    private fb: FormBuilder
    ) { }
    
    ngOnInit() {
      this.studyGuideForm = this.fb.group({
        studyGuideName: ['', Validators.required],
        categories: [this.categoryNames, this.categoriesValidator()],
        description: [''],
        flashCards: this.fb.array([
          this.fb.group({
            front: ['', Validators.required],
            back: ['', Validators.required]
          })
        ])
      });
      this.categoriesFound = this.categoryInputCtrl.valueChanges
        .pipe(
          debounceTime(250),
          startWith(''),
          filter(value => value && value.length > 2),
          filter(value => !this.categoryNames.includes(value.toLowerCase())),
          switchMap(value => this.categoryService.search(value, this.categoryNames))
        );
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
    if (!this.autoComplete.isOpen) {
      this.addCategory(event.value);
      event.input.value = '';
    }
  }

  onCategorySelected(event: MatAutocompleteSelectedEvent) {
    this.addCategory(event.option.value);
    this.categoryInputEl.nativeElement.value = '';
    this.categoryInputCtrl.setValue(null);
  }

  private addCategory(category: string) {
    if (category && category.length > 0 && !this.categoryNames.includes(category.toLowerCase())) {
      this.categoryNames.push(category.toLowerCase());
      this.studyGuideForm.controls['categories'].updateValueAndValidity();
    }
  }

  removeCategory(pos: number) {
    if (this.categoryNames[pos]) {
      this.categoryNames.splice(pos, 1);
      this.studyGuideForm.controls['categories'].updateValueAndValidity();
    }
  }

  get flashCards() {
    return this.studyGuideForm.get('flashCards') as FormArray;
  }

  
  addFlashCard() {
    this.flashCards.push(this.fb.group({ front: '', back: ''}));
    let newFormGroup = this.flashCards.at(this.flashCards.length - 1) as FormGroup;
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
      categories: this.studyGuideForm.get('categories').value,
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

  categoriesValidator(): ValidatorFn {
    return (control: AbstractControl): {[key: string]: any} | null => {
      if (this.studyGuideForm && this.categoryNames.length < 1) {
        return {'invalidCategories': { value: 'At least 1 category is required'}};
      } else {
        return null;
      }
    }
  }

}
