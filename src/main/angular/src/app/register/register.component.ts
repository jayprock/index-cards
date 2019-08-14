import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

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

  constructor(
    private fb: FormBuilder,
    private userService: UserService
  ) { }

  ngOnInit() {
    this.registrationForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password1: ['', [Validators.required, Validators.minLength(this.MIN_PASSWORD_LENGTH)]],
      password2: ['', Validators.required]
    });
  }

  onSubmit() {
    let user: User = {
      username: this.username,
      email: this.email.value,
      password: this.password.value
    };
    this.userService.registerUser(user).subscribe(result => {
      console.log(`Done registering user, got result: ${result}`);
    });
  }

  get username(): string {
    return this.registrationForm.get('username').value;
  }

  get email(): FormControl {
    return this.registrationForm.get('email') as FormControl;
  }

  get password(): FormControl {
    return this.registrationForm.get('password1') as FormControl;
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
    if (this.password.hasError('required')) {
      return "The password is required";
    }
    if (this.password.hasError('minlength')) {
      return `The password must have at least ${this.MIN_PASSWORD_LENGTH} characters`;
    }
    return '';
  }
}
