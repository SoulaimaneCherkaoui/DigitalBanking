import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CustomerService} from "../services/customer.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Customer} from "../model/customer.model";


@Component({
  selector: 'app-editer-customer',
  templateUrl: './editer-customer.component.html',
  styleUrls: ['./editer-customer.component.css'] // Corrected property name with 's'
})

export class EditerCustomerComponent implements OnInit{
  editCustomerFormGroup: FormGroup = new FormGroup({});
  customerId!:number;
  cust: Customer={id:0,name:"",email:""};
  constructor(private fb:FormBuilder,private customerService : CustomerService,private router:Router,private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.customerId = this.route.snapshot.params['id'];

    this.customerService.getCustomerById(this.customerId).subscribe({
      next: (value) => {
        this.cust.id = this.customerId;
        this.cust.name = value.name;
        this.cust.email = value.email;


        // Once data is available, set up the form group

      },
      error: err => {
        console.log(err);
      }
    });
    this.editCustomerFormGroup = this.fb.group({
      id: this.fb.control(this.customerId),
      name: this.fb.control(null, [Validators.required]),
      email: this.fb.control(null, [Validators.required, Validators.email])
    });
  }




  handleEditCustomer() {
    this.cust=this.editCustomerFormGroup.value;
    this.customerService.editCustomer(this.customerId,this.cust).subscribe({
      next : data => {
        alert("Customer has been successfully edited!")
        //this.newCustomerFormGroup.reset();
        this.router.navigateByUrl("/admin/customers");
      },
      error:err => {
        console.log(err);
      }
    })
  }

}
