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
  reviews = [{name: "Đặng Thành Nhân", urlImg:"https://bootdey.com/img/Content/avatar/avatar4.png",
  job:"DEV", review:"So beautiful"},
  {name: "Nguyễn Văn Tèo", urlImg:"https://bootdey.com/img/Content/avatar/avatar3.png",
  job:"CEO Google", review:"WOW"},
  {name: "Trần Thị Nụ", urlImg:"https://bootdey.com/img/Content/avatar/avatar8.png",
  job:"Manager Youtube", review:"Cheap and quality products."}]
  slideConfig = this.sliderService.getReviewSlider();
  constructor(private sliderService:SliderService) { }

  ngOnInit(): void {
  }

}
