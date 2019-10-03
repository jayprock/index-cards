import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PasswordReset } from '../models/password-reset';
import { REST_PATHS } from 'src/app/rest-paths';
import { User } from '../models/user';
import { UserLogin } from '../models/user-login';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(private http: HttpClient) { }

    registerUser(user: User): Observable<User> {
        return this.http.post<User>(REST_PATHS.users, user);
    }

    isUsernameAvailable(username: string): Observable<boolean> {
        return this.http.get<boolean>(`${REST_PATHS.users}/username/${username}`);
    }

    isEmailAvailable(email: string): Observable<boolean> {
        return this.http.get<boolean>(`${REST_PATHS.users}/email/${email}`);
    }

    login(userLogin: UserLogin): Observable<User> {
        return this.http.post<User>(REST_PATHS.sessions, userLogin);
    }

    forgotPassword(email: string): Observable<any> {
        return this.http.put<any>(`${REST_PATHS.users}/password-forgot`, email);
    }

    // POST because not idempotent
    resetPasswordUnauthenticated(passwordReset: PasswordReset): Observable<any> {
        return this.http.post<any>(`${REST_PATHS.users}/password-reset`, passwordReset);
    }
}