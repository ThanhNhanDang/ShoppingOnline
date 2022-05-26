import { CheckoutPayload } from './../../payload/CheckoutPayload';
import { WardPayload } from './../../payload/DeliveryAddress/WardPayload';
import { DistrictPayload } from './../../payload/DeliveryAddress/DistrictPayload';
import { ProvincePayload } from './../../payload/DeliveryAddress/ProvincePayload';
import { CartPayload } from './../../payload/CartPayload';
import { HttpServiceService } from './../../service/httpService/http-service.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss']
})
export class CheckoutComponent implements OnInit {
  paymentMethods=[{id:1, name:"Paypal", des:"Build more this function"},
  {id:2, name:"Payoneer", des:"Build more this function"},
  {id:3, name:"Check Payment", des:"Build more this function"},
  {id:4, name:"Direct Bank Transfer", des:"Build more this function"},
  {id:5, name:"Cash on Delivery", des:"Build more this function"},]
  fullname= this.http.getLoginDataByKey("name");
  mobile = this.http.getLoginDataByKey("mobile");
  gender = this.http.getLoginDataByKey("gender");
  email = this.http.getLoginDataByKey("email")
  address :string = "";
  paymentMethod:string ="";
  cart!:CartPayload[];
  provinces!: ProvincePayload[];
  districts!:DistrictPayload[];
  wards!:WardPayload[];
  selectedProvince!:number;
  selectedDistrict!:number;
  selectedWards!:number;

  i = 0;
  subTotal = 0;
  shippingCost = 3;
  grandTotal = 0;
  constructor(private http:HttpServiceService){}
  ngOnInit(): void {
    this.http.setTitle("E Store-Check out")
    this.getAll();
    this.getDeliveryAddress();
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
    });
  }

  getDeliveryAddress(){
    this.http.getRequest("/Vietnamese-Administrative-Unit/provinces/get-all","").subscribe(data=>{
      this.provinces = data;
      this.selectedProvince = this.provinces[0].id;
      this.http.postRequest("/Vietnamese-Administrative-Unit/districts/get-all-by-province-id",{provinceId:this.selectedProvince}).subscribe(data=>{
        this.districts = data;
        this.selectedDistrict = this.districts[0].id;
        this.http.postRequest("/Vietnamese-Administrative-Unit/wards/get-all-by-district-id",{districtId:this.selectedDistrict}).subscribe(data=>{
          this.wards = data;
          this.selectedWards = this.wards[0].id;
        },error=>{
          alert("Ward not found")
        })
      },error=>{
        alert("District not found")
      })
    },error=>{
      alert("Province not found")
    })
  }

  onChangeProvince(){
    console.log(this.selectedProvince)
    this.http.postRequest("/Vietnamese-Administrative-Unit/districts/get-all-by-province-id",{provinceId:this.selectedProvince}).subscribe(data=>{
      this.districts = data;
      this.selectedDistrict = this.districts[0].id;
      this.http.postRequest("/Vietnamese-Administrative-Unit/wards/get-all-by-district-id",{districtId:this.selectedDistrict}).subscribe(data=>{
        this.wards = data;
        this.selectedWards = this.wards[0].id;
      },error=>{
        alert("Ward not found")
      })
    },error=>{
      alert("District not found")
    })
  }
  onChangeDistrict (){
    this.http.postRequest("/Vietnamese-Administrative-Unit/wards/get-all-by-district-id",{districtId:this.selectedDistrict}).subscribe(data=>{
      this.wards = data;
      this.selectedWards = this.wards[0].id;
    },error=>{
      alert("Ward not found")
    })
  }

  clickPaymentMethod(item:any){
    this.paymentMethod = item;
  }

  clickPlaceOrder(){
    if(this.address == ""){
      alert("Delivery address can't be empty");
      return;
    }
    if(this.paymentMethod == ""){
      alert("Payment Method can't be empty");
      return;
    }
    let request = new CheckoutPayload(this.http.getLoginDataByKey("id"),
    this.grandTotal, this.paymentMethod, this.address, this.selectedProvince,
    this.selectedDistrict, this.selectedWards);
    this.http.postRequest("/order/checkout-order",request).subscribe(()=>{
      this.http.sendBottomBarWhenCheckout();
      alert("Successful")
    }, error=>{
      alert(error.error.message);
    })

  }
}
