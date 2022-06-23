import { OrderPayload } from './../../../payload/OrderPayload';
import { HttpService } from './../../../service/httpService/http.service';

import { Component, OnInit } from '@angular/core';
import { ThemeOption } from 'ngx-echarts';

interface jsonDataChart {
  value: number;
  name: String;

}
@Component({
  selector: 'app-pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.scss']
})
export class PieChartComponent implements OnInit {
  options: any
  theme!: string | ThemeOption;
  dataChart!: jsonDataChart[];
  legendData!: String[];
  constructor(private http: HttpService) {

  }

  ngOnInit(): void {
    // this.subscription = this.echartService.getLineChart().subscribe(data=>{
    //   this.initLineChart(data);
    // })
    this.getProductWithTheMostOrders();
    this.initChart()
  }
  initChart() {
    this.theme = 'dark'
    this.options = {
      title: {
        x: 'center',
        text: '10 products with the most orders'
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
      },
      legend: {
        x: 'center',
        y: 'bottom',
        data: this.legendData 
      },
      calculable: true,
      series: [
        {
          name: 'Product Id:',
          type: 'pie',
          radius: [20, 120],
          roseType: 'area',
          data: this.dataChart
        }
      ]
    };
  }

  getProductWithTheMostOrders() {
    this.http.getRequest("/dashboad/get-data/product-with-the-most-orders").subscribe(data => {
      this.legendData = [];
      this.dataChart = [];
      this.handleData(data);
      this.initChart()
    })
  }

  handleData(response: OrderPayload[]) {
    response.forEach(item => {
      this.legendData.push("ID: " + item.productId);
      this.dataChart.push({ value: item.countTheMostOrders, name: "ID: " + item.productId });
    })
  }
}
