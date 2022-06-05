import { Router } from '@angular/router';
import { ProductService } from './../../../service/product/product.service';
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
  constructor(private http: HttpServiceService, private sliderService:SliderService, private productService:ProductService, private router:Router) { }
  search = new SearchAndSortPayload()
  ngOnInit(): void {
    this.search.page = 0;
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
