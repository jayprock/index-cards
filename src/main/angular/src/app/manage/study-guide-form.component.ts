import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { COMMA, SEMICOLON, SPACE } from '@angular/cdk/keycodes';
import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { MatAutocomplete, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { debounceTime, filter, startWith, switchMap } from 'rxjs/operators';

import { IndexCard } from '../core/models/index-card';
import { MatChipInputEvent } from '@angular/material/chips';
import { Observable } from 'rxjs';
import { StudyGuide } from '../core/models/study-guide';
import { StudyGuideCategoryService } from '../core/services/study-guide-category.service';

@Component({
  selector: 'idx-study-guide-form',
  templateUrl: './study-guide-form.component.html',
  styleUrls: ['./study-guide-form.component.css']
})
export class StudyGuideFormComponent implements OnInit {

  @Input() studyGuide: StudyGuide = {
    studyGuideName: '',
    description: '',
    categories: [],
    flashCards: [{ front: '', back: ''}]
  };
  @Input() serverError: string;
  @Output() submitStudyGuide = new EventEmitter<StudyGuide>();
  
  studyGuideForm: FormGroup;
  validationError: string;

  readonly separatorKeysCodes: number[] = [ SPACE, COMMA, SEMICOLON ];
  categoryInputCtrl = new FormControl();
  categoriesFound: Observable<string[]>;

  @ViewChild("categoryInputEl") categoryInputEl: ElementRef<HTMLInputElement>;
  @ViewChild("auto") autoComplete: MatAutocomplete;

  constructor(
    private fb: FormBuilder,
    private categoryService: StudyGuideCategoryService
    ) { }
    
    ngOnInit() {
      // Initialize the Study Guide Form
      this.studyGuideForm = this.fb.group({
        studyGuideName: [this.studyGuide.studyGuideName, Validators.required],
        categories: [this.studyGuide.categories, [this.categoriesValidator()]],
        description: [this.studyGuide.description],
        flashCards: this.fb.array([])
      });
      // Initialize Flash Cards
      this.studyGuide.flashCards.forEach(flashCard => {
        this.flashCards.push(
          this.fb.group({
            front: [flashCard.front, Validators.required],
            back: [flashCard.back, Validators.required]
          })
        );
      });
      // Search for categories when the input value changes
      this.categoriesFound = this.categoryInputCtrl.valueChanges
        .pipe(
          debounceTime(250),
          startWith(''),
          filter(value => value && value.length > 2),
          filter(value => !this.studyGuide.categories.includes(value.toLowerCase())),
          switchMap(value => this.categoryService.search(value, this.studyGuide.categories))
        );
  }

  onSubmit() {
    if (this.studyGuideForm.valid) {
      this.validationError = null;
      this.submitStudyGuide.emit(this.constructStudyGuide());
    } else {
      this.validationError = "Please enter all required fields and try again."
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
    if (category && category.length > 0 && !this.studyGuide.categories.includes(category.toLowerCase())) {
      this.studyGuide.categories.push(category.toLowerCase());
      this.studyGuideForm.controls['categories'].updateValueAndValidity();
    }
  }

  removeCategory(pos: number) {
    if (this.studyGuide.categories[pos]) {
      this.studyGuide.categories.splice(pos, 1);
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
      if (this.studyGuideForm && this.studyGuide.categories.length < 1) {
        return {'invalidCategories': { value: 'At least 1 category is required'}};
      } else {
        let error = null;
        for (let name of this.studyGuide.categories) {
          if (!name.match('^[a-z][a-z0-9-]{1,}$')) {
            error = {'invalidCategories': { value: 'A category was detected that uses an invalid format'}};
            break;
          }
        }
        return error;
      }
    }
  }

  isErrorPresent(): boolean {
    return this.validationError != null || this.serverError != null;
  }

  getErrorMessage(): string {
    if (this.validationError != null) {
      return this.validationError;
    } else {
      return this.serverError;
    }

  }

}
