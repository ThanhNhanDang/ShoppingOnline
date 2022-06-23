import { SearchAndSortPayload } from './../../../payload/SearchAndSortPayload';
import { SliderService } from './../../../service/sliderService/slider.service';
import { HttpServiceService } from './../../../service/httpService/http-service.service';
import { ProductPayload } from './../../../payload/ProductPayload';
import { environment } from './../../../../environments/environment';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-slidebar',
  templateUrl: './slidebar.component.html',
  styleUrls: ['./slidebar.component.css']
})
export class SlidebarComponent implements OnInit {

  baseUrl= environment.urlServe;
  products!: ProductPayload[];
  slideConfig=this.sliderService.getSidebarSlider()
  constructor(private http: HttpServiceService, private sliderService:SliderService) { }
  search = new SearchAndSortPayload()
  ngOnInit(): void {
    this.search.page = 0;
    this.http.postRequest("/product/search",this.search).subscribe(data=>{
      this.products=data.content
    },error=>{
      alert("Server connection error")
    })
  }
 
}
