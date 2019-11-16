import { Component, OnInit } from '@angular/core';

import { UserService } from '../core/services/user.service';

@Component({
  selector: 'idx-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private userService: UserService) { }

  ngOnInit() {
  }

  logout() {
    this.userService.logout().subscribe(result => {
      console.log("logout successful");
    });
  }
}
