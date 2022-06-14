import { environment } from './../../../../environments/environment';
import { Router } from '@angular/router';
import { WishListPayLoad } from './../../../payload/WishListPayload';
import { CartPayload } from './../../../payload/CartPayload';
import { HttpServiceService } from './../../../service/httpService/http-service.service';
import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
@Component({
  selector: 'app-bottom-bar',
  templateUrl: './bottom-bar.component.html',
  styleUrls: ['./bottom-bar.component.scss'],

})
export class BottomBarComponent implements OnInit {
  dropdownCartValue = false;
  cartSub!: Subscription;
  wishListPlus!: Subscription;
  reset!: Subscription;
  checkIsEmptyWishlist = false;
  cartItem = 0;
  wishListItem = 0;
  baseUrl = environment.urlServe;
  visiblityState = 'hidden';
  cart!: CartPayload[];
  wishList!: WishListPayLoad[];
  subTotal = 0;
  messageCart = "Your cart has no products.";
  messageWishlist = "Your wishlist has no products.";
  checkIsEmptyCart = false;


  i!: number;
  constructor(public http: HttpServiceService, private router: Router) {
    this.reset = this.http.getSubjectBottomBarForLogin().subscribe(() => {
      this.ngOnInit();
    })

    this.reset = this.http.getBottomBarWhenCheckout().subscribe(() => {
      this.getAllCart({ "userId": this.http.getLoginDataByKey("id") })
    })
  }

  ngOnInit(): void {
    if (!this.http.isLogin()) {
      var request = {
        "userId": this.http.getLoginDataByKey("id"),
      }
      this.getAllCart(request);
      this.getAllWishlist(request);
      this.cartSub = this.http.getSubject().subscribe(() => {
        this.getAllCart(request)
      })
      this.cartSub = this.http.getSubjectItemCartWhenDelete().subscribe(() => {
        this.getAllCart(request)
      })
      this.cartSub = this.http.getSubjectUpdateMiniCart().subscribe(() => {
        this.getAllCart(request)
      })

      this.wishListPlus = this.http.getSubjectWishList().subscribe(() => {
        this.getAllWishlist(request)
      })
    }
  }


  getAllCart(request: any) {
    this.http.postRequest("/cart/get-cart", request).subscribe(data => {
      this.cartItem = data.size;
      this.cart = data.addToCartDtos;
      this.subTotal = 0;
      for (this.i = 0; this.i < this.cart.length; this.i++) {
        this.subTotal += this.cart[this.i].price;
      }
      this.subTotal = Number((Math.round(this.subTotal * 100) / 100).toFixed(2));
      if (this.cart.length == 0)
        this.checkIsEmptyCart = true;
      else this.checkIsEmptyCart = false;
    });
  }

  getAllWishlist(request: any) {
    this.http.postRequest("/wishlist/get-all-by-user", request).subscribe(data => {
      this.wishListItem = data.size;
      this.wishList = data.dtos;
      if (this.wishList.length == 0)
        this.checkIsEmptyWishlist = true;
      else this.checkIsEmptyWishlist = false;
    });
  }

  deleteCart(cartPayload: CartPayload) {
    if (confirm("Are you sure you want to remove this product from your cart ?")) {
      var request = {
        "userId": this.http.getLoginDataByKey("id"),
        "cartId": cartPayload.id
      }
      this.http.deleteRequest("/cart/delete-product-from-cart?userId=" + request.userId + "&cartId=" + request.cartId, "").subscribe(data => {

        this.cart = data.addToCartDtos;
        if (this.cart.length == 0) {
          this.checkIsEmptyCart = true;
        }
        else this.checkIsEmptyCart = false;
        this.cartItem = data.size;
        this.http.sendSubjectItemWhenMiniCartDelete();
        this.subTotal = 0;
        for (this.i = 0; this.i < this.cart.length; this.i++) {
          this.subTotal += this.cart[this.i].price;
        }
        this.subTotal = Number((Math.round(this.subTotal * 100) / 100).toFixed(2));
        alert("Successful")
      }, error => {
        alert(error.error.message)
      });
    }
  }
  clickDetailProduct(productId: string) {
    this.http.clickDetailProduct(productId);

  }

  deleteWishlist(productInWishList: WishListPayLoad) {
    if (confirm("Are you sure you want to remove this product from your wishlist ?")) {
      this.http.deleteRequest("/wishlist/deleteByUser?userId=" + this.http.getLoginDataByKey("id") + "&id=" + productInWishList.id, "").subscribe(data => {
        this.wishList = data.dtos;
        this.wishListItem = data.size;
        alert("Successful")
        if (this.wishList.length == 0) {
          this.checkIsEmptyWishlist = true;
        }
        else
          this.checkIsEmptyWishlist = false;
        this.http.sendSubjectItemWhenMiniWishlistDelete();
      }, error => {
        alert("Can't delete");
      });
    }
  }
}
