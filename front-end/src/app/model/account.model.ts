import {Customer} from "./customer.model";

export interface account{
  id : string,
  balance : number,
  createAt: string,
  status:string,
  customerDTO : Customer,
  interest : number,
  type : string,
  overDraft : number
}
