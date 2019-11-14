import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Principal } from '../models/principal';
import { UserService } from '../services/user.service';

@Injectable({
  providedIn: 'root'
})
export class PrincipalResolverService implements Resolve<Principal> {

  constructor(private userService: UserService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Principal> {
    return this.userService.getPrincipal();
  }
}
