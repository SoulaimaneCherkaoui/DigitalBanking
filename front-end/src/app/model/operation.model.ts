import {account} from "./account.model";

export interface operation{
    id:number;
   opeationDate:string;
  amount:number;
  description:string;
  type:string;
  account:account;

}
