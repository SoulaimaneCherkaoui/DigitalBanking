import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateFn,
  GuardResult,
  MaybeAsync, Router,
  RouterStateSnapshot
} from '@angular/router';
import {Injectable} from "@angular/core";
import {AuthService} from "../services/auth.service";


@Injectable({
  providedIn:'root'
})
export class AuthorizationGuard implements CanActivate{
  constructor(private authService :AuthService,private router:Router) {
  }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    if(this.authService.roles.includes("ADMIN")){
      return true;
    }else{
      this.router.navigateByUrl("/admin/notAuthorized")
      return false;
    }


  }

}
