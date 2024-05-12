import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {CustomerService} from "../services/customer.service";
import {AccountsService} from "../services/accounts.service";
import {Router} from "@angular/router";
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'app-my-history',
  templateUrl: './my-history.component.html',
  styleUrl: './my-history.component.css'
})
export class MyHistoryComponent implements OnInit{
  operations:any;
  public dataSource : any;
  customer:any;
  errorMessage!:string;
  searshformGroup!  : FormGroup;
  public displayedColumns: Iterable<string> = ['id','operationDate','amount','type','description']
  @ViewChild(MatPaginator) paginator! : MatPaginator;
  @ViewChild(MatSort) sort! : MatSort;

  constructor(private accountService:AccountsService,private fb : FormBuilder,private router :Router) {
  }

  ngOnInit(): void {
    this.searshformGroup=this.fb.group({
      keyword : this.fb.control("")
    });
    this.accountService.getOperation().subscribe({
      next : (value) => {
        this.operations=value;
        console.log(value);
        this.dataSource=new MatTableDataSource(this.operations);
        this.dataSource.paginator=this.paginator;
        this.dataSource.sort=this.sort;
      },
      error : err => {
        this.errorMessage=err.message;
      }
    })}
}
