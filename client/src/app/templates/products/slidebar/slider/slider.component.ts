import { SearchAndSortPayload } from './../../../../payload/SearchAndSortPayload';
import { environment } from './../../../../../environments/environment';
import { SliderService } from './../../../../service/sliderService/slider.service';
import { HttpServiceService } from './../../../../service/httpService/http-service.service';
import { ProductPayload } from './../../../../payload/ProductPayload';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-slider',
  templateUrl: './slider.component.html',
  styleUrls: ['./slider.component.css']
})
export class SliderComponent implements OnInit {
  baseUrl= environment.urlServe;
  products!: ProductPayload[];
  slideConfig=this.sliderService.getSidebarSlider()
  constructor(private http: HttpServiceService, private sliderService:SliderService) { }
  search = new SearchAndSortPayload()
  ngOnInit(): void {
    this.search.page = 1;
    this.http.postRequest("/product/search",this.search).subscribe(data=>{
      this.products=data.content
    },error=>{
      alert("Server connection error")
    })
  }
}
