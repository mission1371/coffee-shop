import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {catchError, Observable, of, throwError} from 'rxjs';
import {AppRoutingService} from '../app-routing.service';

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {
  constructor(private router: AppRoutingService) {
  }

  intercept(request: HttpRequest<void>, next: HttpHandler): Observable<any> {
    return next.handle(request).pipe(
      catchError((response: HttpErrorResponse) => {
        if (this.isUnauthorized(response) || this.isForbidden(response)) {
          if (this.router.isLoginPage()) {
            return throwError(() => response);
          } else {
            return of(this.router.navigateToLoginPage());
          }
        } else {
          return throwError(() => response);
        }
      })
    );
  }

  private isUnauthorized(response: HttpErrorResponse): boolean {
    return response.status === 401;
  }

  private isForbidden(response: HttpErrorResponse): boolean {
    return response.status === 403;
  }

}
