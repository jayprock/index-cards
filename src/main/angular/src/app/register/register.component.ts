import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { User } from '../core/models/user';
import { UserService } from '../core/services/user.service';

@Component({
  selector: 'idx-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registrationForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private userService: UserService
  ) { }

  ngOnInit() {
    this.registrationForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password1: ['', Validators.required],
      password2: ['', Validators.required]
    });
  }

  onSubmit() {
    let user: User = {
      username: this.username,
      email: this.email,
      password: this.password
    };
    this.userService.registerUser(user).subscribe(result => {
      console.log(`Done registering user, got result: ${result}`);
    });
  }

  get username(): string {
    return this.registrationForm.get('username').value;
  }

  get email(): string {
    return this.registrationForm.get('email').value;
  }

  get password(): string {
    return this.registrationForm.get('password1').value;
  }
}
