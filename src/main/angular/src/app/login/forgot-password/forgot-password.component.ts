import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { ErrorDetails } from 'src/app/core/models/error-details';
import { UserService } from '../../core/services/user.service';

@Component({
  selector: 'idx-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  forgotPasswordForm: FormGroup;
  submitted = false;
  error: ErrorDetails = null;
  errorResponse = null;

  constructor(
    private fb: FormBuilder,
    private userService: UserService
  ) { }

  ngOnInit() {
    this.forgotPasswordForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    })
  }

  onSubmit() {
    if (this.forgotPasswordForm.valid) {
      this.userService.resetPassword(this.forgotPasswordForm.get('email').value)
        .subscribe(result => {
          this.submitted = true;
        }, error => {
          console.log("Made it here")
          this.error = {
            serverError: true,
            message: ''
          };
          this.errorResponse = error;
        });
    } else {
      this.forgotPasswordForm.updateValueAndValidity();
    }
  }

  isError(): boolean {
    if (this.error && this.error.serverError) {
      return true;
    } else {
      return false;
    }
  }

}
