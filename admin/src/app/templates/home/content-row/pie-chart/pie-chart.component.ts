
import { EchartServiceService } from './../../../service/echartService/echart-service.service';
import { Component, OnInit } from '@angular/core';
import { ThemeOption } from 'ngx-echarts';


@Component({
  selector: 'app-pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.scss']
})
export class PieChartComponent implements OnInit {
  options:any
  theme!: string | ThemeOption;
  
  constructor() {
  }

  ngOnInit(): void {
    // this.subscription = this.echartService.getLineChart().subscribe(data=>{
    //   this.initLineChart(data);
    // })
    this.initChart()
  }
  initChart(){
    this.theme = 'dark'
    this.options = {
      title: {
        x: 'center',
        text: 'Storage Information'
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
      },
      legend: {
        x: 'center',
        y: 'bottom',
        data: ['rose1', 'rose2', 'rose3', 'rose4', 'rose5', 'rose6', 'rose7', 'rose8']
      },
      calculable: true,
      series: [
        {
          name: 'area',
          type: 'pie',
          radius: [30, 110],
          roseType: 'area',
          data: [
            { value: 10, name: 'rose1' },
            { value: 5, name: 'rose2' },
            { value: 15, name: 'rose3' },
            { value: 25, name: 'rose4' },
            { value: 20, name: 'rose5' },
            { value: 35, name: 'rose6' },
            { value: 30, name: 'rose7' },
            { value: 40, name: 'rose8' }
          ]
        }
      ]
    };
  }
}
