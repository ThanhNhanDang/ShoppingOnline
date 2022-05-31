import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { environment } from './../../../environments/environment';
import { ProvincePayload } from './../../payload/DeliveryAddress/ProvincePayload';
import { Gender } from './../../payload/Gender';
import { DeliveryAddress } from './../../payload/DeliveryAddress/DeliveryAddress';
import { UserProfile } from './../../payload/UserProfile';
import { OrderPayload } from './../../payload/OrderPayload';
import { HttpServiceService } from './../../service/httpService/http-service.service';
import { Component, OnInit } from '@angular/core';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';

@Component({
  selector: 'app-my-account',
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.css']
})
export class MyAccountComponent implements OnInit {
  baseUrl = environment.urlServe;
  avatar !: string;
  fileName !: string;
  file!: File;
  checkPasswordChange = false;
  form!: FormGroup;

  deliveryAddressId!: number;
  checkEditDelivery = false;

  provinces!: ProvincePayload[];
  selectedProvince!: number;
  selectedDistrict!: number;
  selectedWards!: number;

  userPayload!: UserProfile;
  deliveryAddress: DeliveryAddress[] = [];
  userId!: number;
  active = false;
  orders!: OrderPayload[];
  genders: Gender[] = [
    { value: 'Male' },
    { value: 'Female' },
    { value: 'Prefer not to say' }
  ];

  mustMath = true
  changePass = { email: "", oldPassword: "", password: "", confirmPassword: "" }
  constructor(private formBuilder: FormBuilder, private http: HttpServiceService, private router: Router) { }

  ngOnInit(): void {
    this.http.setTitle("E Store-My account")
    this.userId = this.http.getLoginDataByKey("id");
    this.form = this.formBuilder.group({
      oldPassword: new FormControl(null, Validators.required),
      password: new FormControl(null, Validators.required),
      confirmPassword: new FormControl(null),
    },
    )
    this.userProfile();
    this.http.postRequest("/order/get-my-orders", { userId: this.userId }).subscribe(data => {
      this.orders = data;
    }, error => {
      console.log(error.error.message);
    })

    this.http.postRequest("/delivery-address/get-all-by-user?userId=" + this.userId, "").subscribe(data => {
      this.deliveryAddress = data;
      if (this.deliveryAddress.length == 0)
        return;
      this.getDeliveryAddress();
    }, error => {
      console.log(error.error.message);
    })
  }

  userProfile() {
    this.http.postRequest("/user/profile", { userId: this.userId }).subscribe(data => {
      this.userPayload = data;
      this.deliveryAddressId = this.userPayload.deliveryAddressId;
      this.avatar = this.baseUrl + this.userPayload.fileId
      if (data.role_id == 1)
        this.active = true;

    }, error => {
      alert(error.error.message);
    })
  }
  onFileChanged(files: any) {
    if (files.length === 0)
      return;
    this.file = files.item(0)
    this.fileName = this.file.name;
    var mimeType;
    mimeType = files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      alert("Only images are supported.")
      return;
    }
    var reader = new FileReader();
    reader.readAsDataURL(files[0]);
    reader.onload = (_event) => {
      this.avatar = reader.result as string;
    }
  }
  logout() {
    this.http.logout();
  }
  clickDetail(product_id: string) {
    this.http.clickDetailProduct(product_id)
  }

  editDelivery() {
    this.checkEditDelivery = !this.checkEditDelivery;
  }
  getDeliveryAddress() {
    this.http.getRequest("/Vietnamese-Administrative-Unit/provinces/get-all", "").subscribe(data => {
      this.provinces = data;
      this.getDistrictAndWard()
    }, error => {
      alert("Province not found")
    })
  }

  getDistrictAndWard() {
    this.deliveryAddress.forEach(value => {

      this.http.postRequest("/Vietnamese-Administrative-Unit/districts/get-all-by-province-id", { provinceId: value.provinceId }).subscribe(data => {
        value.districts = data;
        console.log(value.districts)
        this.http.postRequest("/Vietnamese-Administrative-Unit/wards/get-all-by-district-id", { districtId: value.districtId }).subscribe(data => {
          value.wards = data
          console.log(value.wards)
        }, error => {
          alert("Ward not found")
        })
      }, error => {
        alert("District not found")
      })
    })


  }

  onChangeProvince(item: DeliveryAddress) {
    this.http.postRequest("/Vietnamese-Administrative-Unit/districts/get-all-by-province-id", { provinceId: item.provinceId }).subscribe(data => {
      item.districts = data;
      item.districtId = item.districts[0].id
      this.http.postRequest("/Vietnamese-Administrative-Unit/wards/get-all-by-district-id", { districtId: item.districtId }).subscribe(data => {
        item.wards = data;
        item.wardId = item.wards[0].id;
      }, error => {
        alert("Ward not found")
      })
    }, error => {
      alert("District not found")
    })
  }
  onChangeDistrict(item: DeliveryAddress) {
    this.http.postRequest("/Vietnamese-Administrative-Unit/wards/get-all-by-district-id", { districtId: item.districtId }).subscribe(data => {
      item.wards = data;
      item.wardId = item.wards[0].id;

    }, error => {
      alert("Ward not found")
    })
  }

  updateMyAccount() {
    if (this.file != null) {
      this.userPayload.image_url = this.fileName
      this.http.pushFileToStorage(`/user/update/image/${this.userPayload.fileId}`, this.file,).subscribe((res: any) => {
        this.userPayload.fileId = res.id;
        this.http.putRequest("/user/update", this.userPayload).subscribe(() => {
          alert("Update successful.")
        }, error => {
          alert(error.error.message);
        })
      }, error => {
        alert(error.error.message)
      })
    }
    else {
      this.http.putRequest("/user/update", this.userPayload).subscribe(() => {
        alert("Update successful.")
      }, error => {
        alert(error.error.message)
      })
    }
    this.http.setUpdate(this.userPayload);
  }

  updateDeliveryAddress(item: DeliveryAddress) {
    this.http.putRequest("/delivery-address/update", item).subscribe(() => {
      alert("Update successful.");
    }, error => {
      alert(error.error.message);
    })

  }

  deleteDeliveryAddress(delAdd: DeliveryAddress) {
    if (confirm("Are you sure you want to delete this address?")) {
      this.http.deleteRequest("/delivery-address/delete?id=" + delAdd.id + "&userId=" + delAdd.userId + "&deliveryAddressId=" + this.deliveryAddressId, "").subscribe(() => {
        this.deliveryAddress.forEach(function (item, index, object) {
          if (item.id === delAdd.id) {
            object.splice(index, 1);
          }
        })
        alert("Successful.")
      }, error => {
        alert(error.error.message)
      })
    }
  }

  changePassword() {
    if (!this.MustMatch())
      return;
    this.changePass.email = this.userPayload.email;
    this.http.putRequest("/user/change-password", this.changePass).subscribe(() => {
      alert("Successful.");
      this.http.logout();
      this.router.navigateByUrl("/login");

    }, error => {
      alert("Error: " + error.error.message);
    })
  }
  get f() { return this.form.controls }
  MustMatch() {
    if (this.changePass.password != this.changePass.confirmPassword) {
      this.mustMath = false
      console.log(this.mustMath)
      return false;
    }
    this.mustMath = true
    console.log(this.mustMath)
    return true;
  }
}
