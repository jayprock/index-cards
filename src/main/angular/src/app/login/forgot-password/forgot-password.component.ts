import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { ErrorDetails } from 'src/app/core/models/error-details';
import { MESSAGE_KEYS } from '../../core/services/message-keys';
import { MessageConsumerService } from '../../core/services/message-consumer.service';
import { PasswordForgot } from '../../core/models/password-forgot';
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
  recaptchaResponse: string;
  error: ErrorDetails = { serverError: false, message: '', recaptchaError: false};

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

  onRecaptchaReady(response: string) {
    this.recaptchaResponse = response;
  }

  onSubmit() {
    if (this.forgotPasswordForm.valid && this.recaptchaResponse) {
      let passwordForgot: PasswordForgot = {
        email: this.email,
        recaptchaResponse: this.recaptchaResponse
      };
      this.userService.forgotPassword(passwordForgot)
        .subscribe(result => {
          if (result.recaptchaApiResponse.success && result.emailExists) {
            this.messageService.postMessage(MESSAGE_KEYS.email, this.email);
            this.router.navigateByUrl('/password-reset-email');
          } else if (result.recaptchaApiResponse.success) {
            this.error.recaptchaError = false;
            this.error.serverError = true;
            this.error.message = "No account found";  
          } else {
            this.error.recaptchaError = true;
            this.error.serverError = true;
            this.error.message = "Recaptcha verification failed";            
          }
        }, error => {
          this.error.recaptchaError = false;
          this.error.serverError = true;
          this.error.message = "The request could not be completed due to a server error";    
        });
    } else if (this.forgotPasswordForm.valid) {
      this.error.recaptchaError = true;
      this.error.serverError = false;
      this.error.message = '';
    } else {
      this.forgotPasswordForm.updateValueAndValidity();
      this.error.recaptchaError = !this.recaptchaResponse;
    }
  }

  get email(): string {
    return this.forgotPasswordForm.get('email').value;
  }

}
