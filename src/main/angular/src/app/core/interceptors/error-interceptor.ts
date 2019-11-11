import { EMPTY, Observable, throwError } from 'rxjs';
import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from '@angular/common/http';

import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';

/**
 * Error interceptor is the first to access the error. It does not give 
 * the component an opportunity to handle its own errors.
 */
@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private router: Router) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((err: HttpErrorResponse) => {
        if (err.status == 401) {
          console.error("TODO - handle unauthorized access");
          return throwError(err);
        } else {
          return throwError(err);
        }
      })
    );
  }

}