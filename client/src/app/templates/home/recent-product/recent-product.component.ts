import { Router } from '@angular/router';
import { environment } from './../../../../environments/environment';
import { ProductService } from './../../../service/product/product.service';
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
  baseUrl= environment.urlServe;
  products!: ProductPayload[];
  slideConfig=this.sliderService.getRecentProduct();
  constructor(private router: Router, private http: HttpServiceService, private sliderService:SliderService, private productService:ProductService) { }

  ngOnInit(): void {
    this.http.getRequest("/product/get-products-by-category?page=1","").subscribe(data=>{
      this.products=data.content
    },error=>{
      alert("Server connection error")
    })
  }
  clickAddCart(obj:any){
    if(!this.http.checkLogin())
      return 
    this.productService.addCart(obj, 1);
    
  }
  buyNow(item:any){
    if(!this.http.checkLogin())
      return 
    this.productService.addCart(item, 1);
    this.router.navigateByUrl("/checkout");
  }
  clickDetail(productId:string){
    this.http.clickDetailProduct(productId);
  }
  clickAddWishlist(obj:any){
    if(!this.http.checkLogin())
      return 
    this.productService.addWishList(obj);
  }
}
