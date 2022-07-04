import { environment } from './../../../environments/environment';
import { ProductService } from '../../service/product/product.service';
import { HttpServiceService } from '../../service/httpService/http-service.service';
import { ProductPayload } from '../../payload/ProductPayload';
import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  @Input() item !: ProductPayload;
  baseUrl = environment.urlServe + "/products";

  constructor(private router: Router, private http: HttpServiceService, private productService: ProductService) { }

  ngOnInit(): void {
  }
  clickCartPlus(productPayload: ProductPayload) {
    if (!this.http.checkLogin())
      return
    this.productService.addCart(productPayload, 1)
  }

  clickProductDetail(productId: string) {
    this.http.clickDetailProduct(productId);
  }
  clickWishListPlus(item: ProductPayload) {
    if (!this.http.checkLogin())
      return
    this.productService.addWishList(item)
  }
  buyNow(item: any) {
    if (!this.http.checkLogin())
      return
    this.productService.addCart(item, 1);
    this.router.navigateByUrl("/checkout");
  }

}
