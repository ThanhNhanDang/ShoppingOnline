import { SliderService } from './../../../service/sliderService/slider.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-brand',
  templateUrl: './brand.component.html',
  styleUrls: ['./brand.component.css']
})
export class BrandComponent implements OnInit {
  slideConfig=this.sliderService.getBrandSlider();
  urlImgs=this.sliderService.getBrandUrlImgs();
  constructor(private sliderService:SliderService) { }

  ngOnInit(): void {
  }

}
