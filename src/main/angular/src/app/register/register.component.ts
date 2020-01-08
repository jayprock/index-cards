import { AbstractControl, AsyncValidatorFn, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { debounceTime, distinctUntilChanged, first, map, switchMap } from 'rxjs/operators';

import { ErrorDetails } from '../core/models/error-details';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { User } from '../core/models/user';
import { UserService } from '../core/services/user.service';

@Component({
  selector: 'idx-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  readonly USERNAME_MIN_LENGTH = 3;
  readonly USERNAME_MAX_LENGTH = 50;
  readonly MIN_PASSWORD_LENGTH = 8;
  
  registrationForm: FormGroup;
  recaptchaResponse: string;
  error: ErrorDetails;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit() {
    this.registrationForm = this.fb.group({
      username: ['', [
          Validators.required, 
          Validators.minLength(this.USERNAME_MIN_LENGTH), 
          Validators.maxLength(this.USERNAME_MAX_LENGTH), 
          Validators.pattern("[a-zA-Z0-9]*$")
        ],  this.usernameNotTakenValidator()],
      email: ['', [
        Validators.required, 
        Validators.email
      ], this.emailNotTakenValidator()],
      password1: ['', [
        Validators.required, 
        Validators.minLength(this.MIN_PASSWORD_LENGTH), 
        (control) => this.validatePasswords(control, 'password1') 
      ] ],
      password2: ['', [
        Validators.required, 
        Validators.minLength(this.MIN_PASSWORD_LENGTH), 
        (control) => this.validatePasswords(control, 'password2') 
      ] ]
    });
    this.clearErrors();
  }

  onRecaptchaReady(response: string) {
    this.recaptchaResponse = response;
  }

  onSubmit() {
    if (this.registrationForm.valid && this.recaptchaResponse) {
      let user: User = {
        username: this.username.value,
        email: this.email.value,
        password: this.password.value
      };
      this.userService.registerUser({ user: user, recaptchaResponse: this.recaptchaResponse })
          .subscribe(result => {
            if (result.recaptchaApiResponse.success == true) {
              this.clearErrors();
              this.router.navigateByUrl('/dashboard');
            } else {
              this.error.recaptchaError = true;
              this.error.serverError = true;
              this.error.message = "Recaptcha verification failed";
            }
          }, error => {
            this.error.serverError = true;
            this.error.recaptchaError = false;
            this.error.message = "Registration could not be completed due to a server error";
          });
    } else if (this.registrationForm.valid) {
      this.error.recaptchaError = true;
    } else {
      this.registrationForm.updateValueAndValidity();
      this.error.recaptchaError = !this.recaptchaResponse;
    }

  }

  get username(): AbstractControl {
    return this.registrationForm.get('username');
  }

  get email(): AbstractControl {
    return this.registrationForm.get('email');
  }

  get password(): AbstractControl {
    return this.registrationForm.get('password1');
  }

  get confirmPassword(): AbstractControl {
    return this.registrationForm.get('password2');
  }

  getUsernameError(): string {
    if (this.username.hasError('required')) {
      return "Username is required";
    }
    if (this.username.hasError('minlength')) {
      return `Username must have at least ${this.USERNAME_MIN_LENGTH} characters`;
    }
    if (this.username.hasError('maxlength')) {
      return `Username cannot be longer than ${this.USERNAME_MAX_LENGTH} characters`;
    }
    if (this.username.hasError('pattern')) {
      return "Invalid characters. Username must be alpha-numeric.";
    }
    if (this.username.hasError('usernameTaken')) {
      return "Username is not available";
    }
    return '';
  }

  getEmailError(): string {
    if (this.email.hasError('required')) {
      return "Email is required";
    }
    if (this.email.hasError('email')) {
      return "Not a valid email address";
    }
    if (this.email.hasError('emailTaken')) {
      return "Email already registered. Log in instead?"
    }
    return '';
  }

  getPasswordError(): string {
    return this.evaluatePasswordErrorMessage(this.password);
  }

  getPasswordConfirmationError(): string {
    return this.evaluatePasswordErrorMessage(this.confirmPassword);
  }

  private evaluatePasswordErrorMessage(passwordControl: AbstractControl): string {
    if (passwordControl.hasError('required')) {
      return "This field is required";
    }
    if (passwordControl.hasError('minlength')) {
      return `The password must have at least ${this.MIN_PASSWORD_LENGTH} characters`;
    }
    if (passwordControl.hasError('passwordMismatch')) {
      return "The passwords do not match";
    }
    return '';
  }

  usernameNotTakenValidator(): AsyncValidatorFn {
    return this.notTakenValidator(value => this.userService.isUsernameAvailable(value), 'usernameTaken');
  }

  emailNotTakenValidator(): AsyncValidatorFn {
    return this.notTakenValidator(value => this.userService.isEmailAvailable(value), 'emailTaken');
  }

  private notTakenValidator(check: (value: string) => Observable<boolean>, errorName: string): AsyncValidatorFn {
    return (control: AbstractControl) => control.valueChanges
      .pipe(
        debounceTime(500),
        distinctUntilChanged(),
        switchMap(value => check(value)),
        map((result: boolean) => (result ? null : { [errorName] : true})),
        first()
      )
  }

  private clearErrors(): void {
    this.error = { serverError: false, message: '', recaptchaError: false};
  }

  validatePasswords(control: AbstractControl, name: string) {
    if (this.registrationForm === undefined || this.password.value === '' || this.confirmPassword.value === '') {
      return null;
    } else if (this.password.value === this.confirmPassword.value) {
      if (name === 'password1' && this.confirmPassword.hasError('passwordMismatch')) {
        this.password.setErrors(null);
        this.confirmPassword.updateValueAndValidity();
      } else if (name === 'password2' && this.password.hasError('passwordMismatch')) {
        this.confirmPassword.setErrors(null);
        this.password.updateValueAndValidity();
      }
      return null;
    } else {
      return {'passwordMismatch': { value: 'The provided passwords do not match'}};
    }    
  }

}
