import { ErrorHandler, Injectable, Injector, NgZone } from '@angular/core';

import { Router } from '@angular/router';

/**
 * The GlobalErrorHandler gets the error last. If the ErrorInterceptor 
 * and the component do not act on an error, it will end up here.
 */
@Injectable()
export class GlobalErrorHandler implements ErrorHandler {
    
    constructor(private ngZone: NgZone, private injector: Injector) { }

    handleError(error: any): void {
        if (error.status == 404) {
            // I had to use ngZone here to avoid errors, not sure why
            this.ngZone.run(() => this.injector.get<Router>(Router).navigateByUrl('not-found', {skipLocationChange: true})).then();
        } else {
            throw error;
        }
    }

}