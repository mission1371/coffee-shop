import {Injectable} from '@angular/core';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AppRoutingService {
  constructor(private router: Router) {
  }

  navigateToMainPage(): Promise<boolean> {
    return this.router.navigate(['/']);
  }

  isLoginPage(): boolean {
    return this.router.url.startsWith('/authentication/login');
  }

  navigateToLoginPage(): Promise<boolean> {
    return this.router.navigate(['/authentication']);
  }

  navigateToErrorPage(message?: string): Promise<boolean> {
    return this.router.navigate(['/error'], {state: {errorMessage: message}});
  }

}
