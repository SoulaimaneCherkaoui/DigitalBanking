import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CustomersComponent} from "./customers/customers.component";
import {AccountsComponent} from "./accounts/accounts.component";
import {NewCustomerComponent} from "./new-customer/new-customer.component";
import {LoginComponent} from "./login/login.component";
import {AdminTemplateComponent} from "./admin-template/admin-template.component";
import {AuthenticationGuard} from "./guards/authentication.guard";
import {AuthorizationGuard} from "./guards/authorization.guard";
import {NotAuthorizedComponent} from "./not-authorized/not-authorized.component";
import {EditerCustomerComponent} from "./editer-customer/editer-customer.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {OperationComponent} from "./operation/operation.component";
import {MyHistoryComponent} from "./my-history/my-history.component";
import {TransactionsComponent} from "./transactions/transactions.component";

const routes: Routes = [
  {path:"login",component:LoginComponent},
  {path:"",redirectTo : "/login" , pathMatch : "full"},
  {path:"admin",component:AdminTemplateComponent,canActivate : [AuthenticationGuard],
    children:[
      {path:"customers",component:CustomersComponent},
      {path:"accounts",component:AccountsComponent},
      {path:"dashboard",component:DashboardComponent},
      {path:"history",component:MyHistoryComponent},
      {path:"transactions",component:TransactionsComponent},
      {path:"new-customer",component:NewCustomerComponent,canActivate:[AuthorizationGuard],data:{role:"ADMIN"}},
      {path:"editCustomer/:id",component:EditerCustomerComponent,canActivate:[AuthorizationGuard],data:{role:"ADMIN"}},
      {path:"notAuthorized",component:NotAuthorizedComponent}

    ]},


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
