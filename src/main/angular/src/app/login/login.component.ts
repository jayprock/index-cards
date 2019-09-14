import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

import { User } from '../core/models/user';
import { UserService } from '../core/services/user.service';

@Component({
  selector: 'idx-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private userService: UserService
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
        let user: User = result;
        console.log(`Logged in user ${user.id}`)
      }, error => {
        console.log("Username of password invalid")
      });
    } else {
      this.loginForm.updateValueAndValidity();
    }
  }

}
