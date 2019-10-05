import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { ActivatedRoute } from '@angular/router';
import { UserService } from '../../core/services/user.service';

@Component({
  selector: 'idx-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  userId: number;
  tokenId: string;
  resetPasswordForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private userService: UserService
  ) { }

  ngOnInit() {
    this.tokenId = this.route.snapshot.paramMap.get('tokenId');
    this.userId = +this.route.snapshot.queryParamMap.get('id');
    this.resetPasswordForm = this.fb.group({
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.resetPasswordForm.valid) {
      this.userService.resetPasswordUnauthenticated({
        userId: this.userId,
        token: this.tokenId,
        newPassword: this.password
      }).subscribe(result => {
        console.log("TODO - do something");
      });
    } else {
      this.resetPasswordForm.updateValueAndValidity();
    }
  }

  get password(): string {
    return this.resetPasswordForm.get('password').value;
  }


  get confirmPassword(): string {
    return this.resetPasswordForm.get('confirmPassword').value;
  }
}
