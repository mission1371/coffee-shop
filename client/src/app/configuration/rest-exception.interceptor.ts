import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {catchError, Observable, of, throwError} from 'rxjs';
import {AppRoutingService} from '../app-routing.service';

@Injectable()
export class RestExceptionInterceptor implements HttpInterceptor {
  constructor(private router: AppRoutingService) {
  }

  intercept(request: HttpRequest<void>, next: HttpHandler): Observable<any> {
    return next.handle(request).pipe(
      catchError((response: HttpErrorResponse) => {
        if (this.isGenericError(response)) {
          return of(this.router.navigateToErrorPage(response.error.message));
        } else if (this.isServiceIsUnavailable(response) || this.isGatewayTimeout(response)) {
          return of(this.router.navigateToErrorPage('We will come back soon!'));
        }
        return throwError(() => response);
      })
    );
  }

  private isServiceIsUnavailable(response: HttpErrorResponse): boolean {
    return response.status === 503;
  }

  private isGatewayTimeout(response: HttpErrorResponse): boolean {
    return response.status === 504;
  }

  private isGenericError(response: HttpErrorResponse): boolean {
    return response?.error?.code && response.error.code === 'generic-exception';
  }
}
