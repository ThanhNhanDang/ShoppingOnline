import { LineChartPayload } from './../../../payload/lineChart.payload';
import { EchartServiceService } from './../../../service/echartService/echart-service.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-line-chart',
  templateUrl: './line-chart.component.html',
  styleUrls: ['./line-chart.component.scss']
})
export class LineChartComponent implements OnInit {
  chartOption!: any;
  subscription!:Subscription;
  isDarkMode:boolean=false;
  theme!:string;
  chartData:LineChartPayload[]=[
    {
      name:"Mon",
      value:150
    },
    {
      name:"Tue",
      value:230
    },
    {
      name:"Wed",
      value:224
    },
    {
      name:"Thu",
      value:218
    },
    {
      name:"Fri",
      value:135
    },
    {
      name:"Sat",
      value:147
    },
    {
      name:"Sun",
      value:260
    },
  ];
  
  constructor() {
  }

  ngOnInit(): void {
    // this.subscription = this.echartService.getLineChart().subscribe(data=>{
    //   this.initLineChart(data);
    // })
    this.initLineChart(this.chartData)
  }
  
  private initLineChart(chartData:LineChartPayload[]){
    //this.theme= this.isDarkMode ? 'dark':'';
    this.theme = 'dark'
    this.chartOption ={
      title: {
        text: 'Product'
      },
      tooltip:{
        trigger: 'axis'
      },
      background: 'transparent',
      legend: {
        data: ['rose1', 'rose2', 'rose3']
      },
      xAxis: {
        type: 'category',
        data: chartData.map(m=>({
          value: m.name
        })),
      },
      yAxis: {
        type: 'value'
      },
      series: [{
        name: 'rose1',
        data: chartData.map(m=>({
          value: m.value
        })),
        type: 'line'
      },
      {
        name: 'rose2',
        data: [323, 345, 232, 351, 406, 400, 390],
        type: 'line'
      },
      {
        name: 'rose3',
        data: [500, 231,132, 304, 406, 234, 100],
        type: 'line'
      }]
    }
    console.log(this.chartOption);
  }

}
//[150, 230, 224, 218, 135, 147, 260],