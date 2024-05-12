import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Chart } from 'chart.js';
import { getRelativePosition } from 'chart.js/helpers';
import {AccountsService} from "../services/accounts.service";
import {CustomerService} from "../services/customer.service";
import {MatTableDataSource} from "@angular/material/table";
import _default from "chart.js/dist/plugins/plugin.tooltip";
import numbers = _default.defaults.animations.numbers;
import {operation} from "../model/operation.model";
import {account} from "../model/account.model";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls:['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  operations!:Array<operation>;
  credit!: number;
  debit!: number;
  current!:number;
  saving!:number;
  over!:Array<number>;
  balanceL!:Array<number>;
  balanceI!:Array<number>;
  interest!:Array<number>;

  errorMessage!:string;
  @ViewChild('myChart1', { static: true }) myChart1: ElementRef<HTMLCanvasElement> | undefined;
  @ViewChild('myChart2', { static: true }) myChart2: ElementRef<HTMLCanvasElement> | undefined;
  @ViewChild('myChart3', { static: true }) myChart3: ElementRef<HTMLCanvasElement> | undefined;
  @ViewChild('myChart4', { static: true }) myChart4: ElementRef<HTMLCanvasElement> | undefined;

  accounts!: Array<account>;
constructor(private accountService:AccountsService,private customersService:CustomerService) {
}

  ngOnInit(): void {
  this.accountService.getAccount().subscribe({
    next:value => {
      this.accounts=value;
      this.GetSumAccountType();
      this.setChart2();
    }
  })

    this.accountService.getOperation().subscribe({
      next : (value) => {
        this.operations=value;

this.getSumType()


this.setChart();
      },
      error : err => {
        this.errorMessage=err.message;
      }
    })

  }

  GetSumAccountType(){
  this.saving=0;
  this.current=0;
    this.interest = [];
    this.balanceI = [];
    this.over = [];
    this.balanceL = [];
  this.accounts.forEach((account)=>  {

    if (account.type === 'SavingAccount') {
      this.saving += account.balance;
      console.log(account.interest);

      this.interest.push(account.interest);
      this.balanceI.push(account.balance);


    } else if (account.type === 'CurrentAccount') {
      this.current += account.balance;
      this.over.push(account.overDraft);
      this.balanceL.push(account.balance);
    }
  });
  }
  setChart2(){
    if (!this.myChart2 || !this.myChart1 || !this.myChart4) {
      console.error('Canvas elements not found.');
      return;
    }
    const ctx1: any = this.myChart1.nativeElement.getContext('2d');
    const ctx2: any = this.myChart2.nativeElement.getContext('2d');
    const ctx4: any = this.myChart4.nativeElement.getContext('2d');

    this.setupChart(ctx1, this.getData(),'line');
    this.setupChart(ctx4, this.getData3(),'line');

    this.setupChart(ctx2, this.getDataForBarChart(),'bar');
  }
  setChart(){
    if (!this.myChart3) {
      console.error('Canvas elements not found.');
      return;
    }
    const ctx3: any = this.myChart3.nativeElement.getContext('2d');
    this.setupChart(ctx3, this.getDataForPieChart(),'pie');
  }
  getSumType(){
    this.credit=0;
    this.debit=0;
    this.operations.forEach((item) => {

      if (item.type === 'CREDIT') {
        this.credit += item.amount;

      } else if (item.type === 'DEBIT') {
        this.debit += item.amount;
      }
    });
  }

  setupChart(ctx: any, data: any,tyype : any) {
    return new Chart(ctx, {
      type: tyype,
      data: data,
      options: {
        onClick: (e: any) => {
          this.handleClick(e, ctx);
        },
        responsive: true,
        maintainAspectRatio: false,
      }
    });
  }

  getData3() {

    // Sample data
    return {
      labels: this.over,
      datasets: [{
        label: 'OverDraft By Balance',
        data: this.balanceL,
        fill: true,
        borderColor: 'rgb(75, 192, 192)',
        tension: 0.1
      }]
    };
  }
  getData() {

    // Sample data
    return {
      labels: this.interest,
      datasets: [{
        label: 'Interest By Balance',
        data: this.balanceI,
        fill: true,
        borderColor: 'rgb(75, 192, 192)',
        tension: 0.1
      }]
    };
  }
  getDataForBarChart() {
    // Sample data for a bar chart
    return {
      labels: ['CurrentBank', 'SavingBank'],
      datasets: [{
        label: 'Sample Data',
        data: [this.current,this.saving],
        backgroundColor: 'rgba(75, 192, 192, 0.2)', // Background color of the bars
        borderColor: 'rgba(75, 192, 192, 1)', // Border color of the bars
        borderWidth: 1, // Border width of the bars
      }]
    };
  }
  getDataForPieChart() {
  console.log(this.credit);
    // Sample data for a pie chart
    return {
      labels: ['Debit', 'Credit'],
      datasets: [{
        label: 'Sample Data',
        data: [this.debit,this.credit], // Data values for each slice of the pie
        backgroundColor: [
          'rgba(255, 99, 132, 0.2)',
          'rgba(54, 162, 235, 0.2)',

        ],
        borderColor: [
          'rgba(255, 99, 132, 1)',
          'rgba(54, 162, 235, 1)',

        ],
        borderWidth: 1,
      }]
    };
  }


  getData2() {
    // Sample data
    return {
      labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
      datasets: [{
        label: 'Sample Data',
        data: [65, 59, 4, 5, 80, 55, 40],
        fill: false,
        borderColor: 'rgb(75, 100, 92)',
        tension: 0.1
      }]
    };
  }

  handleClick(event: any, chart: any) {
    const canvasPosition = getRelativePosition(event, chart);

    // Substitute the appropriate scale IDs
    const dataX = chart.scales.x.getValueForPixel(canvasPosition.x);
    const dataY = chart.scales.y.getValueForPixel(canvasPosition.y);

    console.log('Clicked at:', { x: dataX, y: dataY });
  }
}
