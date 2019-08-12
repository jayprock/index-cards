import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { REST_PATHS } from 'src/app/rest-paths';
import { User } from '../models/user';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(private http: HttpClient) { }

    registerUser(user: User): Observable<User> {
        return this.http.post<User>(REST_PATHS.users, user);
    }
}