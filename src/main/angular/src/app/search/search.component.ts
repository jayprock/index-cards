import { Component, OnInit, ViewChild } from '@angular/core';

import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { StudyGuide } from '../core/models/study-guide';
import { StudyGuideService } from '../core/services/study-guide.service';

@Component({
  selector: 'idx-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  @ViewChild('searchForm') searchForm: NgForm;

  searchParam: string;
  studyGuides: StudyGuide[];
  searchExecuted = false;
  lastExecutedSearchParam: string;

  constructor(private studyGuideService: StudyGuideService, private router: Router) { }

  ngOnInit() {
  }

  onSearch() {
    if (this.searchForm.form.valid) {
      this.searchExecuted = true;
      this.studyGuideService.searchStudyGuide(this.searchParam).subscribe(result => {
        this.lastExecutedSearchParam = this.searchParam;
        this.studyGuides = result;
      })
    }
  }

}
