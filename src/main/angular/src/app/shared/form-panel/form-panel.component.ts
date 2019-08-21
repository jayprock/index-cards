import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'idx-form-panel',
  templateUrl: './form-panel.component.html',
  styleUrls: ['./form-panel.component.css']
})
export class FormPanelComponent implements OnInit {

  @Input() formHeader: string;

  constructor() { }

  ngOnInit() {
    if (this.formHeader === null) {
      throw new Error("The form header is required!");
    }
  }

}
