import { environment } from './../../../../environments/environment';
import { SliderService } from './../../../service/sliderService/slider.service';
import { HttpServiceService } from './../../../service/httpService/http-service.service';
import { ProductPayload } from './../../../payload/ProductPayload';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-recent-product',
  templateUrl: './recent-product.component.html',
  styleUrls: ['./recent-product.component.css']
})
export class RecentProductComponent implements OnInit {
  baseUrl = environment.urlServe;
  products!: ProductPayload[];
  slideConfig = this.sliderService.getRecentProduct();
  constructor(private http: HttpServiceService, private sliderService: SliderService) { }

  ngOnInit(): void {
    this.http.getRequest("/product/get-products-by-category?page=1", "").subscribe(data => {
      this.products = data.content
    }, error => {
      alert("Server connection error")
    })
  }
}
