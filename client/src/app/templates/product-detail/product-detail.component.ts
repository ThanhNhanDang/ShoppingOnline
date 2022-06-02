import { environment } from './../../../environments/environment';
import { Router, ActivatedRoute } from '@angular/router';
import { ProductService } from './../../service/product/product.service';
import { SliderService } from './../../service/sliderService/slider.service';
import { ReviewPayload } from './../../payload/ReviewPayload';
import { HttpServiceService } from './../../service/httpService/http-service.service';
import { ProductPayload } from './../../payload/ProductPayload';
import { Component, OnInit } from '@angular/core';
import { ProductDetailUrlImg } from 'src/app/payload/ProductDetailUrlImg';
import { Location } from '@angular/common';
@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {
  user = {
    name: "",
    urlImg: "",
    date: "",
    fileId: this.http.getLoginDataByKey("fileId")
  }
  discount = 10 / 100; // 10%;
  discountPrice !: number;
  baseUrl = environment.urlServe;
  checkLoad = false;
  quantity = 1;
  product!: ProductPayload;
  totalPrice = 0;
  review = "";
  reviews!: ReviewPayload[];
  totalReview = 0;
  slideConfig = this.sliderService.getSlideConfigDetail();
  slideConfigNav = this.sliderService.getSlideConfigNavDetail()
  productId!: string
  productDetailUrlImg!: ProductDetailUrlImg[];

  date!: Date;
  last_date!: string;

  ratting = [true, true, true, true, true];
  numberStar = 5;
  products!: ProductPayload[];
  slideConfigRelated = this.sliderService.getRelateProduct();
  constructor(private activatedRoute: ActivatedRoute, private _location: Location, private http: HttpServiceService, private sliderService: SliderService, private productService: ProductService, private router: Router) {
  }
  ngOnInit(): void {
    this.http.setTitle("E Store-Product detail")
    this.reviews = []
    this.user.urlImg = this.http.getLoginDataByKey("image_url");
    this.user.name = this.http.getLoginDataByKey("name");
    this.activatedRoute.queryParams.subscribe(data => {
      if (data.key != null || data.key != "") {
        this.productId = data.key;
        this.getProduct();
        this.getReviews();
      }
    })
  }
  getProduct() {
    this.http.postRequest("/product/get-product", { productId: this.productId }).subscribe(data => {
      this.product = data;

      this.totalPrice = this.product.price;
      this.discountPrice = this.totalPrice + this.totalPrice * this.discount;
      this.getDetailUrlImg();
      this.getReviews()
      //lấy sản phẩm đồng thời lấy những sản phẩm có liên quan
      this.http.getRequest("/product/get-products-by-category?page=0&id=" + this.product.category_id, "").subscribe(data => {
        this.products = data.content
        console.log(this.products);

      }, error => {
        alert("Server connection error")
      })
    }, error => {
      alert(error.error.message);
      this._location.back();
    })
  }
  getReviews() {
    this.http.postRequest("/review/get/by-product", { productId: this.productId }).subscribe(data => {
      this.reviews = data.dtos;
      this.totalReview = data.size;
      this.reviews.forEach(value => {
        value.starReview = [false, false, false, false, false]
        for (var i = 0; i < value.ratting; i++)
          value.starReview[i] = true;
      })
    }, error => {
      alert(error.error.message)
    })
  }
  getDetailUrlImg() {
    this.http.postRequest("/product-detail/get-product-detail-by-product", { productId: this.productId, cateId: this.product.category_id }).subscribe(data => {
      this.productDetailUrlImg = data;
    }, error => {
      alert(error.error.message)
      this._location.back();
    })
  }
  handleMinus() {
    this.quantity--;
    if (this.quantity < 1) {
      this.quantity = 1;
    }
    this.totalPrice = this.product.price * this.quantity;
    this.discountPrice = this.totalPrice + this.totalPrice * this.discount;

  }
  handlePlus() {
    this.quantity++;
    this.totalPrice = this.product.price * this.quantity;
    this.discountPrice = this.totalPrice + this.totalPrice * this.discount;

  }
  onInputChanged(event: any) {
    this.quantity = event;
    this.totalPrice = this.product.price * this.quantity;
  }

  sendMessage() {
    if (!this.http.checkLogin())
      return;
    this.date = new Date();
    this.last_date = this.date.toISOString()
    let starReview = [false, false, false, false, false]
    for (var i = 0; i < this.numberStar; i++)
      starReview[i] = true;
    const reviewPayload = new ReviewPayload(this.http.getLoginDataByKey("id"),
      this.productId, this.review, this.last_date, this.user.urlImg,
      this.user.name, this.numberStar);
    if (this.review == null || this.review == "")
      return;

    this.totalReview++;
    this.http.postRequest("/review/add", reviewPayload).subscribe(data => {

      this.reviews.unshift(data)
      this.reviews[0].starReview = starReview;
      this.reviews[0].fileIdUser = this.user.fileId;
    }, error => {
      console.log(error.error.message)
    })
    this.review = "";
    this.ratting = [true, true, true, true, true]
  }

  clickProductDetail(productId: string) {
    this.http.clickDetailProduct(productId);

  }

  clickAddCart(item: any) {
    if (!this.http.checkLogin())
      return
    this.productService.addCart(item, this.quantity);
  }


  buyNow(item: any) {
    if (!this.http.checkLogin())
      return
    this.productService.addCart(item, this.quantity);
    this.router.navigateByUrl("/checkout")
  }
  clickAddWishlist(item: any) {
    if (!this.http.checkLogin())
      return
    this.productService.addWishList(item);
  }

  backClicked() {
    this._location.back();
  }

  change(index: any) {
    for (var i = 0; i < 5; i++) {
      if (i <= index) {
        this.ratting[i] = true;
      } else
        this.ratting[i] = false;
    }
    this.numberStar = index + 1;
  }

}
