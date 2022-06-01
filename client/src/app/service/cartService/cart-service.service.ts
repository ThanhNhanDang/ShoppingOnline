import { ProductPayload } from './../../payload/ProductPayload';
import { HttpServiceService } from './../httpService/http-service.service';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartServiceService {
  public cartServiceEven = new BehaviorSubject({});
  constructor(private http: HttpServiceService) { }

  addCart(obj:any){
    var request = {
      "productId":obj.productId,
      "userId": obj.userId, 
      "quantity":obj.quantity
    }
    this.http.postRequest("/cart/add-product", request).subscribe((data:any)=>{
      this.http.sendClickSubject();
      alert("Successful") 
    },
    error=>{
      alert(error.error.message)
    })
  }

  addCart2(obj:any){
    var request = {
      "productId":obj.productId,
      "userId": obj.userId, 
      "quantity":obj.quantity
    }
    this.http.postRequest("/cart/add-product", request).subscribe((data:any)=>{
      this.http.sendClickSubject();
      alert("Successful") 
    },
    error=>{
      console.log(error.error.message);
    })
  }
 
}
