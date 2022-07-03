import { ProductDetailUrlImg } from './../../payload/ProductDetailPayload';
import { HttpService } from './../../service/httpService/http.service';
import { environment } from './../../../../environments/environment';
import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-add-image-product-detail',
  templateUrl: './add-image-product-detail.component.html',
  styleUrls: ['./add-image-product-detail.component.scss']
})

export class AddImageProductDetailComponent implements OnInit {
  productId!: string;
  cateId!: string;
  files: File[] = [];
  productDetailUrlImg: ProductDetailUrlImg[] = [];
  detail!: ProductDetailUrlImg;
  selectedFile!: File;
  baseUrl = environment.urlServe;
  date: any;
  latest_date: any;
  test!: string;
  constructor(private activatedRouter: ActivatedRoute, private http: HttpService, private _location: Location) { }

  ngOnInit(): void {
    this.activatedRouter.queryParams.subscribe(data => {
      if (data.product == null || data.product == "" || data.category == null || data.category == "") {
        return;
      }
      else {
        this.productId = data.product;
        this.cateId = data.category;
        this.getDetailUrlImg();
      }
    })
  }

  onChangeOneFile(files: any, item: ProductDetailUrlImg, indexOfelement: number) {
    if (files.length === 0)
      return;
    var mimeType;
    mimeType = files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      alert("Only images are supported.")
      return;
    }
    var reader = new FileReader();
    reader.readAsDataURL(files[0]);
    reader.onload = (_event) => {
      item.srcImg = reader.result as string;
      item.file = files.item(0);
      item.checkOnChange = false;
    }
  }

  async onChangeMultipleFile(files: any) {
    if (files.length === 0)
      return;
    var mimeType;
    for (let i = 0; i < files.length; i++) {
      mimeType = files[i].type;
      if (mimeType.match(/image\/*/) == null) {
        console.log("Only images are supported.")
      }
      else {
        await this.readFile(files[i]);
      }
    }
  }

  readFile(file: File) {
    let product: ProductDetailUrlImg = new ProductDetailUrlImg();
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = (_event) => {
      product.srcImg = reader.result as string;
      product.file = file;
    }
    this.productDetailUrlImg.unshift(product);
  }

  getDetailUrlImg() {
    this.http.postRequest("/product-detail/get-product-detail-by-product", { productId: this.productId, cateId: this.cateId }).subscribe(data => {
      this.productDetailUrlImg = data;
      this.productDetailUrlImg.forEach(element => {
        element.srcImg = this.baseUrl + "/products/" + this.productId + "/" + element.urlImg;
        element.checkUpload = true;
        element.checkOnChange = true;
      });
    }, error => {
      alert(error.error.message)
      this._location.back();
    })
  }

  deleteNow(item: ProductDetailUrlImg) {
    if (!item.checkUpload) {
      const index = this.productDetailUrlImg.indexOf(item, 0);
      if (index > -1) {
        this.productDetailUrlImg.splice(index, 1);
      }
      return
    }
    this.http.deleteRequest("/product-detail/delete?id=" + item.id, {}).subscribe(() => {
      const index = this.productDetailUrlImg.indexOf(item, 0);
      if (index > -1) {
        this.productDetailUrlImg.splice(index, 1);
      }
    })
  }

  updateOneItem(item: ProductDetailUrlImg) {
    if (item.file != null) {
      if (!item.checkUpload) { //chưa upload
        this.http.postRequestFileToStorage("/product-detail/add", { productId: this.productId }, item.file).subscribe(data => {
          item.id = data.id;
          item.checkUpload = true;
          item.checkOnChange = true;
        })
        return;
      }
      this.http.putRequestFileToStorage("/product-detail/update", { id: item.id, productId: this.productId }, item.file).subscribe((res: any) => {
        item.checkOnChange = true;
      }, error => {
        alert(error.error.message);
      })
    }
  }
  addAllItem() {
    let check = true;
    let listMultipleFile = []
    let listProductDetail = []
    for (let element of this.productDetailUrlImg) {
      if (!element.checkUpload) { // nếu một item được thêm mới
        check = false;
        listMultipleFile.push(element.file);
        listProductDetail.push({ productId: this.productId });
      }
    }
    if (check) {
      alert("The data has not changed.");
      return;
    }
    this.http.postRequestMulFileToStorage("/product-detail/add/all", listProductDetail, listMultipleFile).subscribe((data) => {
      for (let i = 0; i < data.length; i++) {
        this.productDetailUrlImg[i].id = data[i].id;
      }
      this.productDetailUrlImg.forEach(elment => {
        if (!elment.checkUpload) { elment.checkUpload = true; elment.checkOnChange = true };
      })
    }, error => {
      alert(error.error.message)
    })
  }
  updateAllItem() {
    let check = true;
    let listMultipleFile = []
    let listProductDetail = []
    for (let element of this.productDetailUrlImg) {
      if (!element.checkOnChange) { // nếu một item được chỉnh sửa
        check = false;
        listMultipleFile.push(element.file);
        listProductDetail.unshift({ id: element.id, productId: this.productId });
      }
    }
    if (check) {
      alert("The data has not changed.");
      return;
    }

    this.http.putRequestMulFileToStorage("/product-detail/update/all", listProductDetail, listMultipleFile).subscribe((data) => {
      this.productDetailUrlImg.forEach(elment => {
        if (!elment.checkOnChange) { elment.checkOnChange = true };
      })
    }, error => {
      alert(error.error.message)
    })
  }

  deleteAllItem() {
    this.http.deleteRequest("/product-detail/delete/all?productId=" + this.productId + "", {}).subscribe(() => {
      this.getDetailUrlImg();
    }, error => {
      alert(error.error.message);
    })
  }
}
