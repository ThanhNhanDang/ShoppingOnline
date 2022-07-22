import { environment } from './../../../../environments/environment';
import { OrderPayload } from './../../payload/OrderPayload';
import { HttpService } from './../../service/httpService/http.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { FeedbackPayload } from '../../payload/FeedbackPayload';
import { number } from 'echarts';

@Component({
  selector: 'app-content-row',
  templateUrl: './content-row.component.html',
  styleUrls: ['./content-row.component.scss']
})
export class ContentRowComponent implements OnInit {
  imgURL = environment.urlServe + "/userDefault.png";
  orders: OrderPayload[] = [];
  feedbacks: FeedbackPayload[] = [];
  constructor(private http: HttpService) { }

  ngOnInit(): void {
    this.getAllOrder();
    this.getAllFeedback();
  }

  private getAllOrder() {
    this.http.getRequest("/order/get-all").subscribe(data => {
      this.orders = data;
      // 1656932761

      this.orders.forEach(item => {
        item.orderDate = new Date((+item.orderDate * 1000)).toString().split("GM")[0];
        item.price = this.http.getRoundingNumber(item.price);
      })
    }, error => {
      alert(error.error.message);
    })
  }

  private getAllFeedback() {
    this.http.getRequest("/feedback/get-all").subscribe(data => {
      this.feedbacks = data;
    }, error => {
      alert(error.error.message);
    })
  }

}
