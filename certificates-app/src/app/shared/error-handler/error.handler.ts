import { ErrorHandler, Injectable, NgZone } from '@angular/core';
import { Router } from '@angular/router';

@Injectable()
export class GlobalErrorHandler implements ErrorHandler {

    constructor (private router : Router, private zone : NgZone) {}

    handleError(error: any): void {
        if(error.status === 403) {
            this.zone.run(() => this.router.navigate(['/login']));
        }
        else if(error.status === 404) {
            this.zone.run(() => this.router.navigate(['/not_found']));
        }
        else {
            this.zone.run(() => this.router.navigate(['/error']));
        }
    }
  
  
}