import { SearchAndSortPayload } from './../../../../payload/SearchAndSortPayload';
import { environment } from './../../../../../environments/environment';
import { Router } from '@angular/router';
import { ProductService } from './../../../../service/product/product.service';
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
  constructor(private http: HttpServiceService, private sliderService:SliderService, private productService:ProductService, private router:Router) { }
  search = new SearchAndSortPayload()
  ngOnInit(): void {
    this.search.page = 1;
    this.http.postRequest("/product/search",this.search).subscribe(data=>{
      this.products=data.content
    },error=>{
      alert("Server connection error")
    })
  }
  clickProductDetail(id:string){
    this.http.clickDetailProduct(id); 
  }

  clickAddCart(item:any){
    if(!this.http.checkLogin())
      return 
    this.productService.addCart(item, 1);
  }

  buyNow(item:any){
    if(!this.http.checkLogin())
      return 
    this.productService.addCart(item, 1);
    this.router.navigateByUrl("/checkout");
  }
  clickAddWishlist(item:any){
    if(!this.http.checkLogin())
      return 
    this.productService.addWishList(item);
  }
}
