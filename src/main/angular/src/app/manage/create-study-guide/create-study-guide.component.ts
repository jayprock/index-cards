import { Component, OnInit } from '@angular/core';

import { ActivatedRoute } from '@angular/router';
import { Principal } from '../../core/models/principal';

@Component({
  selector: 'idx-create-study-guide',
  templateUrl: './create-study-guide.component.html',
  styleUrls: ['./create-study-guide.component.css']
})
export class CreateStudyGuideComponent implements OnInit {

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.data
      .subscribe((data: { principal: Principal }) => {
        if (data.principal == null) {
          window.alert("TODO - Design authorized users only notification");
        }
      });
  }

}
