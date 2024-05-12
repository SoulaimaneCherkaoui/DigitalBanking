import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {CustomerService} from "../services/customer.service";
import {Router} from "@angular/router";
import {MatTableDataSource} from "@angular/material/table";
import {AccountsService} from "../services/accounts.service";
import {account} from "../model/account.model";

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrl: './accounts.component.css'
})
export class AccountsComponent implements OnInit{
  accounts:any;
  public dataSource : any;
  customer:any;
  errorMessage!:string;
  searshformGroup!  : FormGroup;
  public displayedColumns: Iterable<string> = ['name','balance','date','type','interest','overDraft']
  @ViewChild(MatPaginator) paginator! : MatPaginator;
  @ViewChild(MatSort) sort! : MatSort;

  constructor(private customerService : CustomerService,private accountService:AccountsService,private fb : FormBuilder,private router :Router) {
  }

  ngOnInit(): void {
    this.searshformGroup=this.fb.group({
      keyword : this.fb.control("")
    });
    this.accountService.getAccount().subscribe({
      next : (value) => {
        this.accounts=value;
        console.log(value.length);
        this.dataSource=new MatTableDataSource(this.accounts);
        this.dataSource.paginator=this.paginator;
        this.dataSource.sort=this.sort;
      },
      error : err => {
        this.errorMessage=err.message;
      }
    })}

  supprimer(element:account) {

  }

  editer(element : account) {

  }



}
