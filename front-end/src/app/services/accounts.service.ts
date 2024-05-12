import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Customer} from "../model/customer.model";
import {account} from "../model/account.model";
import {operation} from "../model/operation.model";
import {transaction} from "../model/transaction.model";

@Injectable({
  providedIn: 'root'
})
export class AccountsService {

  backendHost:string="http://localhost:8085"
  constructor(private  http:HttpClient) { }

  public getAccount():Observable<Array<account>>{
    return this.http.get<Array<account>>(this.backendHost+"/accounts");
  }
  public getOperationById(id:string):Observable<Array<operation>>{
    return this.http.get<Array<operation>>(this.backendHost+"/accounts/"+id+"/operations");
  }
  public getOperation():Observable<Array<operation>>{
    return this.http.get<Array<operation>>(this.backendHost+"/accounts/operations");
  }
  public getTransactions():Observable<Array<transaction>>{
    return this.http.get<Array<transaction>>(this.backendHost+"/transfer");
  }
  public getTransactionsPrediction():Observable<Array<transaction>>{
    return this.http.get<Array<transaction>>(this.backendHost+"/getPredictions");
  }

}
