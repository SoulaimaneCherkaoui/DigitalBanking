import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {jwtDecode} from "jwt-decode";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isAuthenticated : boolean=false;
roles : any;
username : any;
accesToken! : any;
  constructor(private http:HttpClient) { }
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
  }

  logout() {
    this.isAuthenticated=false;
    this.accesToken=undefined;
    this.username=undefined;
    this.roles=undefined;
  }
}
