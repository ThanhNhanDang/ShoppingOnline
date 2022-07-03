import { environment } from './../../../environments/environment';
import { EditProduct } from './../payload/mergePayload/EditProduct';
import { CategoryPayload } from './../payload/CategoryPayload';
import { ProductPayload } from './../payload/productPayload';
import { HttpService } from './../service/httpService/http.service';
import { Component, OnInit } from '@angular/core';
import { DatePipe, Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.scss']
})
export class EditProductComponent implements OnInit {
  imageToShow: any;
  productId!: string;
  productPayload!: ProductPayload;
  categories !: CategoryPayload[];
  editProduct!: EditProduct;
  fileName !: string;
  file!: File;
  date: any;
  latest_date: any;
  baseUrl = environment.urlServe;
  test!: string
  constructor(private activatedRouter: ActivatedRoute, private datepipe: DatePipe, private http: HttpService, private _location: Location) { }

  ngOnInit(): void {
    this.activatedRouter.queryParams.subscribe(data => {
      if (data.product == null || data.product == "") {
        return;
      }
      else {
        this.productId = data.product;
        this.getProduct();
      }
    })
    this.getCategory();
  }
  dateEventEmitter(date: any) {
    this.productPayload.exDate = date;
  }

  getCategory() {
    this.http.getRequest("/category/all").subscribe(data => {
      this.categories = data;
    }, error => {
      alert(error.error.message)
    })
  }

  getProduct() {
    this.http.postRequest("/product/get-product", { productId: this.productId }).subscribe(data => {
      this.productPayload = data;
      this.imageToShow = this.baseUrl + "/products/" + this.productPayload.id + "/" + this.productPayload.urlImg;
      this.productPayload.exDate = this.productPayload.exDate.slice(0, 10);
    }, error => {
      alert(error.error.message)
      this._location.back();
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
      this.imageToShow = reader.result as string;
    }
  }

  update() {
    if (this.productPayload.name == "")
      return alert("Name not be empty");
    if (this.productPayload.price == null || this.productPayload.price <= 0)
      return alert("Invalid price: must > 0.");
    if (this.productPayload.exDate == "")
      return alert("Expride Date not be empty");
    if (this.productPayload.inStock == null || this.productPayload.inStock < 0)
      return alert("Invalid unit in stock.");

    this.date = new Date();
    this.test = this.date.toISOString()
    this.latest_date = this.datepipe.transform(this.productPayload.exDate, 'yyyy-MM-dd');
    this.productPayload.exprideDate = this.latest_date + this.test.slice(10)

    if (this.file != null) {
      this.http.putRequestFileToStorage("/product/update/product-image", this.productPayload, this.file).subscribe(() => {
        alert("Successful")
      }, error => {
        alert(error.error.message)
      })
      return;
    }
    this.http.putRequest("/product/update", this.productPayload).subscribe(() => {
      alert("Successful");
    }, error => {
      alert(error.error.message)
    })

  }
} 
