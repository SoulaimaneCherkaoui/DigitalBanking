import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {CustomerService} from "../services/customer.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Customer} from "../model/customer.model";
import {map} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrl: './customers.component.css'
})
export class CustomersComponent implements OnInit{
  customers:any;
  public dataSource : any;
  errorMessage!:string;
  searshformGroup!  : FormGroup;
  public displayedColumns: Iterable<string> = ['id','name','email','supprimer','editer']
  @ViewChild(MatPaginator) paginator! : MatPaginator;
  @ViewChild(MatSort) sort! : MatSort;

  constructor(private customerService:CustomerService,private fb : FormBuilder,private router :Router) {
  }

  ngOnInit(): void {
    this.searshformGroup=this.fb.group({
      keyword : this.fb.control("")
    });
    this.customerService.getCustomer().subscribe({
      next : value => {
        this.customers=value;
        this.dataSource=new MatTableDataSource(this.customers);
        this.dataSource.paginator=this.paginator;
        this.dataSource.sort=this.sort;
      },
      error : err => {
        this.errorMessage=err.message;
      }
    })
  }

  handleSearshCustomers() {
let kw = this.searshformGroup?.value.keyword;
    this.customerService.searchCustomer(kw).subscribe({
      next : value => {
        this.customers=value;
        this.dataSource=new MatTableDataSource(this.customers);
        this.dataSource.paginator=this.paginator;
        this.dataSource.sort=this.sort;
      },
      error : err => {
        this.errorMessage=err.message;
      }
    })
}

  supprimer(element:Customer) {
this.customerService.deleteCustomer(element.id).subscribe({
  next : (value) =>{

    this.handleSearshCustomers();
  },
  error:err => {
    console.log(err);
  }
})

  }

  editer(element:Customer) {

      this.router.navigateByUrl(`/admin/editCustomer/${element.id}`);


  }
}
