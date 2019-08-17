import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

import { ErrorDetails } from '../core/models/error-details';
import { User } from '../core/models/user';
import { UserService } from '../core/services/user.service';

@Component({
  selector: 'idx-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  
  readonly MIN_PASSWORD_LENGTH = 8;
  
  registrationForm: FormGroup;
  error: ErrorDetails;

  constructor(
    private fb: FormBuilder,
    private userService: UserService
  ) { }

  ngOnInit() {
    this.registrationForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password1: ['', [Validators.required, Validators.minLength(this.MIN_PASSWORD_LENGTH), (control) => this.validatePasswords(control, 'password1') ] ],
      password2: ['', [Validators.required, Validators.minLength(this.MIN_PASSWORD_LENGTH), (control) => this.validatePasswords(control, 'password2') ] ]
    });
    this.error = { serverError: false, message: ''};
  }

  onSubmit() {
    let user: User = {
      username: this.username,
      email: this.email.value,
      password: this.password.value
    };
    this.userService.registerUser(user).subscribe(result => {
      console.log(`Done registering user, got result: ${result}`);
      this.error.serverError = false;
      this.error.message = '';
    }, error => {
      this.error.serverError = true;
      this.error.message = "Registration could not be completed due to a server error";
    });
  }

  get username(): string {
    return this.registrationForm.get('username').value;
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

  getEmailError(): string {
    if (this.email.hasError('required')) {
      return "Email is required";
    }
    if (this.email.hasError('email')) {
      return "Not a valid email address";
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
