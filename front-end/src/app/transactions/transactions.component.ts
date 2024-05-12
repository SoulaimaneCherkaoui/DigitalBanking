import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {AccountsService} from "../services/accounts.service";
import {Router} from "@angular/router";
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.component.html',
  styleUrl: './transactions.component.css'
})
export class TransactionsComponent implements OnInit{
  transactions:any;
  public dataSource : any;
  customer:any;
  errorMessage!:string;
  searshformGroup!  : FormGroup;
  public displayedColumns: Iterable<string> = ['accountSource','oldSoldeSource','newSoldeSource','amount','accountDestination','oldSoldeDestination','newSoldeDestination']
  @ViewChild(MatPaginator) paginator! : MatPaginator;
  @ViewChild(MatSort) sort! : MatSort;

  constructor(private accountService:AccountsService,private fb : FormBuilder,private router :Router) {
  }

  ngOnInit(): void {
    this.searshformGroup=this.fb.group({
      keyword : this.fb.control("")
    });
    this.accountService.getTransactions().subscribe({
      next : (value) => {
        this.transactions=value;
        this.dataSource=new MatTableDataSource(this.transactions);
        this.dataSource.paginator=this.paginator;
        this.dataSource.sort=this.sort;

      },
      error : err => {
        this.errorMessage=err.message;
      }
    })}

  Prediction() {
this.accountService.getTransactionsPrediction().subscribe({
  next: value => {
    this.transactions=value;
    this.router.navigateByUrl("/admin/transactions");
    this.dataSource=new MatTableDataSource(this.transactions);
    this.dataSource.paginator=this.paginator;
    this.dataSource.sort=this.sort;
  }
})
  }
}
