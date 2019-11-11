import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

import { Router } from '@angular/router';
import { User } from '../core/models/user';
import { UserService } from '../core/services/user.service';

@Component({
  selector: 'idx-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  authenticationError = false;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit() {
    this.loginForm = this.fb.group({
      login: [''],
      password: ['']
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      this.userService.login({
        login: this.loginForm.get('login').value,
        password: this.loginForm.get('password').value
      }).subscribe(result => {
        this.authenticationError = false;
        this.router.navigateByUrl('/dashboard');
      }, error => {
        this.authenticationError = true;
      });
    } else {
      this.loginForm.updateValueAndValidity();
    }
  }

}
