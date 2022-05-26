import { SliderService } from './../../../service/sliderService/slider.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-banner',
  templateUrl: './banner.component.html',
  styleUrls: ['./banner.component.css']
})
export class BannerComponent implements OnInit {
  slideConfig=this.sliderSerivce.getHeaderSlider();
  urlImgs=[
    "https://blog.dktcdn.net/files/chup-anh-quan-ao-3.jpg",
    "https://blog.dktcdn.net/files/chup-anh-quan-ao.jpg",
    "https://blog.dktcdn.net/files/chup-anh-quan-ao-2.jpg",
    "https://lamdepwiki.com/wp-content/uploads/2022/03/puma-cali-bold-white-black-564x564-jpg.jpg",
    "https://lamdepwiki.com/wp-content/uploads/2022/03/giay-sneaker-mid-top-564x564-jpg.jpg",
    "https://menback.com/wp-content/uploads/2022/04/Chanel-J12-Caliber-12.2-33mm-564x564.jpg",
    "https://fantasea.vn/wp-content/uploads/2018/02/%E1%BA%A8m-th%E1%BB%B1c-Nga-564x564.jpg",
    "https://familylove.vn/image/cache/catalog/products_2019/23d500387f02109b66947e654886f9cb-564x564.jpg.webp",
  ]
  
  constructor(private sliderSerivce:SliderService) { }

  ngOnInit(): void {
  }

}
