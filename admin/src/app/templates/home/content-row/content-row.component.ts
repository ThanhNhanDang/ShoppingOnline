import { environment } from './../../../../environments/environment';
import { OrderPayload } from './../../payload/OrderPayload';
import { HttpService } from './../../service/httpService/http.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { FeedbackPayload } from '../../payload/FeedbackPayload';

@Component({
  selector: 'app-content-row',
  templateUrl: './content-row.component.html',
  styleUrls: ['./content-row.component.scss']
})
export class ContentRowComponent implements OnInit {
  imgURL = environment.urlServe + "images/avatars/userDefault.png";
  orders: OrderPayload[] = [];
  feedbacks: FeedbackPayload[] = [];
  constructor(private http: HttpService, private router: Router, private activatedRouter: ActivatedRoute) { }

  ngOnInit(): void {
    this.getAllOrder();
    this.getAllFeedback();
  }

  private getAllOrder() {
    this.http.getRequest("/order/get-all").subscribe(data => {
      this.orders = data;
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
