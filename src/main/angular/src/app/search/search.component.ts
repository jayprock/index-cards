import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';

import { Router } from '@angular/router';
import { StudyGuide } from '../core/models/study-guide';
import { StudyGuideService } from '../core/services/study-guide.service';

@Component({
  selector: 'idx-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  
  searchForm: FormGroup;

  studyGuides: StudyGuide[];
  searchExecuted = false;
  lastExecutedSearchParam: string;


  constructor(
    private fb: FormBuilder,
    private studyGuideService: StudyGuideService, 
    private router: Router
  ) { }

  ngOnInit() {
    this.searchForm = this.fb.group({
      searchParam: ['', Validators.required]
    });
  }

  onSearch() {
    if (this.searchForm.valid) {
      this.searchExecuted = true;
      this.studyGuideService.searchStudyGuide(this.searchParam).subscribe(result => {
        this.lastExecutedSearchParam = this.searchParam;
        this.studyGuides = result;
      })
    } else {
      this.searchForm.updateValueAndValidity();
    }
  }

  get searchParam(): string {
    return this.searchForm.get('searchParam').value;
  }

}
