import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {jwtDecode} from "jwt-decode";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isAuthenticated : boolean=false;
roles : any;
username : any;
accesToken! : any;
  constructor(private http:HttpClient,private router:Router) { }
  public login(username : string,password :string){
    let options = {
      headers : new HttpHeaders().set("Content-Type","application/x-www-form-urlencoded")
    }
    let params=new HttpParams().set("username",username).set("password",password);
    return this.http.post("http://localhost:8085/auth/login",params,options);
  }

  loadProfile(data: any) :any{
    this.isAuthenticated=true;
this.accesToken=data['access-token'];
let jwtDecoder:any = jwtDecode(this.accesToken);
this.username = jwtDecoder.sub;
this.roles = jwtDecoder.scope;
//stocker jwt dans local storage pour rester authentifier:
    window.localStorage.setItem("jwt-token",this.accesToken);
  }

  logout() {
    this.isAuthenticated=false;
    this.accesToken=undefined;
    this.username=undefined;
    this.roles=undefined;
    window.localStorage.removeItem("access-token");
    this.router.navigateByUrl("/login")
  }

  loadJwtTokenFromLocalStorage() {
    let token = window.localStorage.getItem("jwt-token");
    if(token){
      this.loadProfile({"access-token" : token});
      this.router.navigateByUrl("/admin/customers")
    }
  }
}
