import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'idx-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  tokenId: string;
  resetPasswordForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) { }

  ngOnInit() {
    let tokenId = this.route.snapshot.paramMap.get('tokenId');
    this.resetPasswordForm = this.fb.group({});
  }

}
