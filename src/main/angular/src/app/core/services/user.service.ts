import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PasswordReset } from '../models/password-reset';
import { Principal } from '../models/principal';
import { REST_PATHS } from 'src/app/rest-paths';
import { User } from '../models/user';
import { UserLogin } from '../models/user-login';
import { UserRegistration } from '../models/user-registration';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(private http: HttpClient) { }

    getPrincipal(): Observable<Principal> {
        return this.http.get<Principal>(REST_PATHS.sessions);
    }

    registerUser(userRegistration: UserRegistration): Observable<any> {
        return this.http.post<any>(REST_PATHS.users, userRegistration);
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

    logout(): Observable<any> {
        return this.http.delete<any>(REST_PATHS.sessions);
    }

    forgotPassword(email: string): Observable<any> {
        return this.http.put<any>(`${REST_PATHS.users}/password-forgot`, email);
    }

    // POST because not idempotent
    resetPasswordUnauthenticated(passwordReset: PasswordReset): Observable<any> {
        return this.http.post<any>(`${REST_PATHS.users}/password-reset`, passwordReset);
    }
}