import { environment } from './../../../../environments/environment';
  import { SliderService } from './../../../service/sliderService/slider.service';
import { ProductPayload } from './../../../payload/ProductPayload';
import { HttpServiceService } from './../../../service/httpService/http-service.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-feature-product',
  templateUrl: './feature-product.component.html',
  styleUrls: ['./feature-product.component.css']
})
export class FeatureProductComponent implements OnInit {
  products!: ProductPayload[];
  baseUrl = environment.urlServe;
  slideConfig = this.sliderService.getRecentProduct()
  constructor(private http: HttpServiceService, private sliderService: SliderService) { }

  ngOnInit(): void {
    this.http.getRequest("/product/get-products-by-category?page=0", "").subscribe(data => {
      this.products = data.content
    }, error => {
      alert("Server connection error")
    })
  }
}
