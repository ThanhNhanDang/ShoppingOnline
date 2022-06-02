import { LineChartPayload } from './../../../payload/lineChart.payload';
import { Subscription } from 'rxjs';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-line-chart1',
  templateUrl: './line-chart1.component.html',
  styleUrls: ['../line-chart/line-chart.component.scss']
})
export class LineChart1Component implements OnInit {
  chartOption!: any;
  subscription!: Subscription;
  isDarkMode: boolean = false;
  theme!: string;
  chartData: LineChartPayload[] = [
    {
      name: "Mon",
      value: 150
    },
    {
      name: "Tue",
      value: 230
    },
    {
      name: "Wed",
      value: 224
    },
    {
      name: "Thu",
      value: 218
    },
    {
      name: "Fri",
      value: 135
    },
    {
      name: "Sat",
      value: 147
    },
    {
      name: "Sun",
      value: 260
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

  private initLineChart(chartData: LineChartPayload[]) {
    //this.theme= this.isDarkMode ? 'dark':'';
    this.theme = 'dark'
    this.chartOption = {
      title: {
        x: 'center',
        text: 'Visitors and orders for a YEAR'
      },
      tooltip: {
        trigger: 'axis'
      },
      background: 'transparent',
      legend: {
        x: 'center',
        y: 'bottom',
        data: ['rose1', 'rose2']
      },
      xAxis: {
        type: 'category',
        data: chartData.map(m => ({
          value: m.name
        })),
      },
      yAxis: {
        type: 'value'
      },
      series: [{
        name: 'rose1',
        data: chartData.map(m => ({
          value: m.value
        })),
        type: 'line'
      }, {
        name: 'rose2',
        data: [323, 345, 232, 351, 406, 400, 390],
        type: 'line'
      }]
    }
    console.log(this.chartOption);
  }

}
