import { CartServiceService } from './../cartService/cart-service.service';
import { ProductPayload } from './../../payload/ProductPayload';
import { HttpServiceService } from './../httpService/http-service.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpServiceService, private cartService: CartServiceService) { }
  addCart(obj: any, quantity: number) {
    if (quantity >= obj.inStock) {
      alert("Quatity invalid !!");
      return;
    }
    var request = {
      "productId": obj.id,
      "userId": this.http.getLoginDataByKey("id"),
      "quantity": quantity
    }
    this.cartService.addCart2(request);
  }

  addWishList(obj: any) {
    var request = {
      "productId": obj.id,
      "userId": this.http.getLoginDataByKey("id"),
      "quantity": "1"
    }
    this.http.postRequest("/wishlist/add", request).subscribe((data: any) => {
      this.http.sendSubjectWishList();
      alert("Successful")
    },
      error => {
        alert(error.error.message)
      })
  }
}
