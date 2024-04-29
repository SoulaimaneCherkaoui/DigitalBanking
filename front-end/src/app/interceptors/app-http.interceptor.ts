import {HttpEvent, HttpHandler, HttpInterceptor, HttpInterceptorFn, HttpRequest} from '@angular/common/http';
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {AuthService} from "../services/auth.service";

@Injectable()
export class AppHttpInterceptor implements HttpInterceptor {
  constructor(private authService : AuthService) {
  }
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log(request.url);
    if(!request.url.includes("/auth/login")){
      let newRequest = request.clone({
      headers : request.headers.set('Authorization','Bearer '+this.authService.accesToken)
    })
      return next.handle(newRequest);}
    else return next.handle(request);

  }

}
