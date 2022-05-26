import { HttpServiceService } from './../../service/httpService/http-service.service';
import { OrderPayload } from './../../payload/OrderPayload';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-my-order',
  templateUrl: './my-order.component.html',
  styleUrls: ['./my-order.component.css']
})
export class MyOrderComponent implements OnInit {
  orders!: OrderPayload[];
  userId!:string
  constructor(private http :HttpServiceService) { }

  ngOnInit(): void {
    this.http.setTitle("E Store-My order")
    this.userId = this.http.getLoginDataByKey("id");
    this.http.postRequest("/order/get-my-orders", {userId: this.userId}).subscribe(data=>{
      this.orders = data;
    }, error=>{
      console.log(error.error.message);
    })
  }
  clickDetail(product_id:string){
    this.http.clickDetailProduct(product_id)
  }
}
