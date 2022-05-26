import { environment } from './../../../../environments/environment';
import { SliderService } from './../../../service/sliderService/slider.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css']
})
export class ReviewComponent implements OnInit {
  baseUrl = environment.urlServe
  reviews = [{name: "Đặng Thành Nhân", urlImg:this.baseUrl+"images/avatars/1/dd0e86cc6c9ae4bc0473c762e275f9c4.jpg",
  job:"DEV", review:"Những món hàng này thật to và tròn, admin rất thích :))"},
  {name: "Nguyễn Văn Tèo", urlImg:"https://bootdey.com/img/Content/avatar/avatar3.png",
  job:"CEO Google", review:"WOW"},
  {name: "Trần Thị Nụ", urlImg:"https://bootdey.com/img/Content/avatar/avatar8.png",
  job:"Manager Youtube", review:"Mình rất thích những món đồ ở đây thật xinh đẹp :3"}]
  slideConfig = this.sliderService.getReviewSlider();
  constructor(private sliderService:SliderService) { }

  ngOnInit(): void {
  }

}
