import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { NavbarComponent } from './navbar/navbar.component';
import {MatToolbar, MatToolbarModule} from "@angular/material/toolbar";
import {MatButton, MatButtonModule} from "@angular/material/button";
import { CustomersComponent } from './customers/customers.component';

import { AccountsComponent } from './accounts/accounts.component';
import {HTTP_INTERCEPTORS, HttpClientModule, provideHttpClient, withFetch} from "@angular/common/http";
import {MatCard, MatCardContent, MatCardHeader, MatCardModule} from "@angular/material/card";
import {MatDivider, MatDividerModule} from "@angular/material/divider";
import {MatSort, MatSortHeader, MatSortModule} from "@angular/material/sort";
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow, MatRow,
  MatTable, MatTableModule
} from "@angular/material/table";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatFormField, MatFormFieldModule, MatLabel} from "@angular/material/form-field";
import {MatIcon, MatIconModule} from "@angular/material/icon";
import {MatInput, MatInputModule} from "@angular/material/input";
import { NewCustomerComponent } from './new-customer/new-customer.component';
import { LoginComponent } from './login/login.component';
import { AdminTemplateComponent } from './admin-template/admin-template.component';
import {AppHttpInterceptor} from "./interceptors/app-http.interceptor";
import { NotAuthorizedComponent } from './not-authorized/not-authorized.component';
import {MatMenu, MatMenuItem, MatMenuModule, MatMenuTrigger} from "@angular/material/menu";
import {MatDrawer, MatDrawerContainer, MatDrawerContent} from "@angular/material/sidenav";
import {MatList, MatListItem, MatListModule} from "@angular/material/list";
import { DashboardComponent } from './dashboard/dashboard.component';
import { MyAccountsComponent } from './my-accounts/my-accounts.component';
import { MyHistoryComponent } from './my-history/my-history.component';
import { OperationComponent } from './operation/operation.component';
import { EditerCustomerComponent } from './editer-customer/editer-customer.component';
import {BaseChartDirective, NgChartsModule} from "ng2-charts";
import { TransactionsComponent } from './transactions/transactions.component';
import { TransferComponent } from './transfer/transfer.component';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    CustomersComponent,
    AccountsComponent,
    NewCustomerComponent,
    LoginComponent,
    AdminTemplateComponent,
    NotAuthorizedComponent,
    MyAccountsComponent,
    MyHistoryComponent,
    OperationComponent,
    EditerCustomerComponent,
    DashboardComponent,
    TransactionsComponent,
    TransferComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatToolbarModule,
    MatButtonModule,
    HttpClientModule,
    MatCardModule,
    MatCardHeader,
    MatDividerModule,
    MatCardContent,
    MatSortModule,
    MatTableModule,
    MatColumnDef,
    MatHeaderCell,
    MatCell,
    MatSortHeader,
    MatCellDef,
    MatHeaderCellDef,
    MatPaginatorModule,
    MatHeaderRow,
    MatRow,
    MatProgressSpinner,
    ReactiveFormsModule,
    BrowserModule,
    MatIcon,
    MatInputModule,
    MatLabel,
    FormsModule,
    MatFormFieldModule,
    MatIconModule,
    MatMenuModule,
    MatMenuTrigger,
    MatDrawerContainer,
    MatDrawerContent,
    MatListModule,
    MatListItem,
    MatMenuItem,
    MatDrawer,
    NgChartsModule
  ],
  providers: [
    {provide : HTTP_INTERCEPTORS,useClass:AppHttpInterceptor,multi:true},
    provideClientHydration(),
    provideAnimationsAsync(),
    provideHttpClient(withFetch())
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
