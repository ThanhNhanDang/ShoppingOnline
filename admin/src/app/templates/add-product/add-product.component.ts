import { Router } from '@angular/router';
import { ProductPayload } from './../payload/productPayload';
import { environment } from './../../../environments/environment';
import { HttpService } from './../service/httpService/http.service';
import { CategoryPayload } from './../payload/CategoryPayload';
import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.scss']
})
export class AddProductComponent implements OnInit {
  selectedFile!: File;
  categories !: CategoryPayload[]
  productPayload = new ProductPayload;
  baseUrl = environment.urlServe;
  date: any;
  latest_date: any;
  test!: string
  file!: File;
  fileName!: string;
  imgURL = this.baseUrl + "/" + "productDefault.png";
  constructor(private http: HttpService, private datepipe: DatePipe, private router: Router) { }

  ngOnInit(): void {
    this.http.getRequest("/category/all").subscribe(data => {
      this.categories = data;
    }, error => {
      alert(error.error.message)
    })
  }
  dateEventEmitter(date: any) {
    this.productPayload.exDate = date
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
      this.imgURL = reader.result as string;

    }
  }


  addNow() {
    if (this.file != null) {
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
      this.http.postRequestFileToStorage("/product/add", this.productPayload, this.file).subscribe(data => {
        alert("Successful");
        this.router.navigate(["/product/edit/detail-img"], { queryParams: { 'category': data.category_id, 'product': data.id } })
      }, error => {
        alert(error.error.message)
      })
    }
  }
}