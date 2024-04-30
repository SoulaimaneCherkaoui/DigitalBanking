import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpInterceptorFn,
  HttpRequest
} from '@angular/common/http';
import {Injectable} from "@angular/core";
import {catchError, Observable, throwError} from "rxjs";
import {AuthService} from "../services/auth.service";

@Injectable()
export class AppHttpInterceptor implements HttpInterceptor {
  constructor(private authService : AuthService) {
  }
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!request.url.includes('/auth/login')) {
      const newRequest = request.clone({
        headers: request.headers.set('Authorization', 'Bearer ' + this.authService.accesToken),
      });
      return next.handle(newRequest).pipe(
        catchError((error: HttpErrorResponse) => {
          if (error.status === 401) {
            this.authService.logout(); // Appel de la fonction pour gérer l'erreur 403
          }
          return throwError(error);
        })
      );
    } else {
      return next.handle(request);
    }
  }
}
