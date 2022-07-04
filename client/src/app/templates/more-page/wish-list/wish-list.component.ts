import { environment } from './../../../../environments/environment';
import { Subscription } from 'rxjs';
import { WishListPayLoad } from './../../../payload/WishListPayload';
import { HttpServiceService } from './../../../service/httpService/http-service.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-wish-list',
  templateUrl: './wish-list.component.html',
  styleUrls: ['./wish-list.component.css']
})
export class WishListComponent implements OnInit {
  wishList!: WishListPayLoad[];
  message= "Your wishlist has no products.";
  checkIsEmpty= false;
  baseUrl= environment.urlServe + "/products";

  changeProductWhenCartMiniDelete! : Subscription;
  constructor(private http:HttpServiceService) {
    this.http.setTitle("E Store-My wishlist")
   }

  getAll(){
    var request={
      "userId": this.http.getLoginDataByKey("id"),
    }
    this.http.postRequest("/wishlist/get-all-by-user", request).subscribe(data=>{
      this.wishList = data.dtos;
      if(this.wishList.length == 0){
        this.checkIsEmpty= true;
      }
      else
        this.checkIsEmpty= false;
    });
  }
  ngOnInit(): void {
    this.getAll()
    this.changeProductWhenCartMiniDelete = this.http.getSubjectItemWhenMiniWishlistDelete().subscribe(()=>{
      this.getAll();
    })
  }
  clickDetail(productId:string){
    this.http.clickDetailProduct(productId);
  }

  addToCart(productInWishList:WishListPayLoad){
    var request = {
      "productId":productInWishList.productId,
      "userId":  this.http.getLoginDataByKey("id"),
      "quantity": productInWishList.quantity
    }
    this.http.postRequest("/cart/add-product", request).subscribe((data:any)=>{
      this.http.sendClickSubject(); 
      this.http.deleteRequest("/wishlist/deleteByUser?userId="+request.userId+"&id="+productInWishList.id,"").subscribe(data=>{
        this.wishList = data.dtos;
        this.http.sendSubjectWishList();
        alert("Successful")
        if(this.wishList.length == 0){
          this.checkIsEmpty= true;
        }
        else
          this.checkIsEmpty= false;
      },error=>{
        alert("Can't delete when adding to cart, please contact admin.");
      })
    },
    error=>{
      alert(error.error.message)
    })
  }

  deleteItem(productInWishList:WishListPayLoad){
    if(confirm("Are you sure you want to remove this product from your wishlist ?")){
      this.http.deleteRequest("/wishlist/deleteByUser?userId="+this.http.getLoginDataByKey("id")+"&id="+productInWishList.id,"").subscribe(data=>{
        this.wishList = data.dtos;
        this.http.sendSubjectWishList();
         alert("Successful")
        if(this.wishList.length == 0){
          this.checkIsEmpty= true;
        }
        else
          this.checkIsEmpty= false;
        
      },error=>{
        alert("Can't delete");
      });
    }
  }
  handleMinus(item:WishListPayLoad) {
    item.quantity--;
    if(item.quantity < 1)
      item.quantity = 1;
  }
  handlePlus(item:WishListPayLoad) {
    item.quantity++;
  }

  onInputChanged(item:WishListPayLoad, event: any){
    item.quantity = event;
  }

}
