import { environment } from './../../../environments/environment';
import { Subscription } from 'rxjs';
import { CartPayload } from './../../payload/CartPayload';
import { HttpServiceService } from './../../service/httpService/http-service.service';
import { Component, OnInit } from '@angular/core';
@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  quantity!:number
  checkBtn = false;
  baseUrl= environment.urlServe;
  itemInCart!:CartPayload;
  cart!:CartPayload[]
  message= "Your cart has no products."
  checkIsEmpty= false
  subTotal = 0;
  shippingCost = 3;
  grandTotal = 0;
  changeProductWhenCartMiniDelete! : Subscription;
  i!:number;
  constructor(private http:HttpServiceService) {
   
   }

  ngOnInit(): void {
    this.http.setTitle("E Store-My cart")
    this.getAll()
    this.changeProductWhenCartMiniDelete = this.http.getSubjectItemWhenMiniCartDelete().subscribe(()=>{
      this.getAll()
    })
  }
  getAll(){
    var request={
      "userId": this.http.getLoginDataByKey("id"),
    }
    this.http.postRequest("/cart/get-cart", request).subscribe(data=>{
      this.cart = data.addToCartDtos;
      for(this.i=0; this.i <  this.cart.length; this.i++){
        this.subTotal+=this.cart[this.i].price;
        this.grandTotal = this.subTotal + this.shippingCost
      }
      if(this.cart.length == 0)
        this.checkIsEmpty = true;
      else this.checkIsEmpty = false;
    });
  }
  btn_minus(quantity:number){
    this.checkBtn=true;
    this.quantity = quantity;
    this.quantity--;
  }

  btn_plus(quantity:number){
    console.log(quantity)
  }

  getQuantity(){
    return this.quantity;
  }

  deleteCart(cartPayload:CartPayload){
    if(confirm("Are you sure you want to remove this product from your cart ?")){
      var request = {
        "userId": this.http.getLoginDataByKey("id"),
        "cartId": cartPayload.id
      }
      this.http.deleteRequest("/cart/delete-product-from-cart?userId="+request.userId+"&cartId="+request.cartId,"").subscribe(data=>
        { 

            this.cart = data.addToCartDtos;
            if(this.cart.length == 0){
              this.checkIsEmpty = true;
            }
            else  this.checkIsEmpty = false;
            this.http.setItemCartWhenDelete(data.size);
            this.http.sendSubjectItemCartWhenDelete();
            this.subTotal= 0;
            for(this.i=0; this.i <  this.cart.length; this.i++){
              this.subTotal+=this.cart[this.i].price;
            }
            this.grandTotal = this.shippingCost + this.subTotal;
            alert("Successful")
          },error=>{
          alert(error.error.message)
        });

    }
  
  }

  clickDetail(productId:string){
    this.http.clickDetailProduct(productId);
  }
  handleMinus(item:CartPayload) {
    item.quantity--;
    if(item.quantity < 1){
      item.quantity = 1;
    }else{
    item.price=item.pricePerItem*item.quantity
    this.subTotal -= item.pricePerItem;
    this.grandTotal -= item.pricePerItem;
    }
  }
  handlePlus(item:CartPayload) {
    item.quantity++;
    item.price = item.pricePerItem*item.quantity;
    this.subTotal += item.pricePerItem; 
    this.grandTotal += item.pricePerItem;
  }

  onInputChanged(item:CartPayload, event: any){
    item.quantity = event;
    item.price = item.pricePerItem*item.quantity;
    this.subTotal=0;
    for(this.i=0; this.i <  this.cart.length; this.i++){
      this.subTotal+=this.cart[this.i].price;
    }
    this.grandTotal = this.shippingCost + this.subTotal;
  }

  updateAll(cart:CartPayload[]){
    if(confirm("Are you sure you want to update all ?")){
      this.http.putRequest("/cart/update-all-quantity-for-cart", cart).subscribe(()=>{
        alert("Update successful ")
        this.getAll()
        this.http.sendSubjectUpdateMiniCart();
      },
      error=>{
        alert("Update Failed")
      })
      
    }else{
      this.getAll()
    }
  }
  updateItem(item:CartPayload){
    var request={
      "id": item.id,
      "price": item.pricePerItem,
      "quantity": item.quantity
    };
     this.http.putRequest("/cart/update-quantity-for-cart", request).subscribe(()=>{
      alert("Update successful ")
      this.http.sendSubjectUpdateMiniCart();
      this.getAll()
    },
    error=>{
      alert("Update Failed")
    })
  }

}
