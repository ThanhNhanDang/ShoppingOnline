import { environment } from './../../../environments/environment';
import { HttpServiceService } from './../../service/httpService/http-service.service';
import { ReviewPayload } from './../../payload/ReviewPayload';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-my-review',
  templateUrl: './my-review.component.html',
  styleUrls: ['./my-review.component.css']
})
export class MyReviewComponent implements OnInit {
  reviews!: ReviewPayload[];
  baseUrl = environment.urlServe
  constructor(private http:HttpServiceService) { }

  ngOnInit(): void {
    this.http.setTitle("E Store-My review")
    this.http.postRequest("/review/get/by-user",{userId:this.http.getLoginDataByKey("id")}).subscribe(data=>{
      this.reviews = data.dtos;
    }, error=>{
      alert(error.error.message);
    })
  }
  clickDetail(product_id:string){
    this.http.clickDetailProduct(product_id)
  }
}
