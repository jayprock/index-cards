import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { ErrorDetails } from 'src/app/core/models/error-details';
import { MESSAGE_KEYS } from '../../core/services/message-keys';
import { MessageConsumerService } from '../../core/services/message-consumer.service';
import { Router } from '@angular/router';
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

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private messageService: MessageConsumerService,
    private router: Router
  ) { }

  ngOnInit() {
    this.forgotPasswordForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    })
  }

  onSubmit() {
    if (this.forgotPasswordForm.valid) {
      this.userService.resetPassword(this.email)
        .subscribe(result => {
          this.messageService.postMessage(MESSAGE_KEYS.email, this.email);
          this.router.navigateByUrl('/password-reset-email');
        }, error => {
          this.error = { serverError: true, message: 'No account exists for this email!'};
        });
    } else {
      this.error = null;
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

  get email(): string {
    return this.forgotPasswordForm.get('email').value;
  }

}
