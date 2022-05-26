import { ProductPayload } from './../payload/productPayload';
import { HttpService } from './../service/httpService/http.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {
  products !: ProductPayload[];
  key!:string;
  result!:number;
  productDelete: ProductPayload[] = [];
  constructor(private router:Router, private http:HttpService, private activatedRouter:ActivatedRoute) { }
  ngOnInit(): void {
    this.activatedRouter.queryParams.subscribe(data=>{
      if(data.key ==null || data.key == ""){
        this.getAllProduct();
        return
      }
      else{
        this.key = data.key;
        this.getSearch();
        return;
      }
    })
   
  }
  getAllProduct(){
    this.http.getRequest("/product/all").subscribe(data=>{
      this.products = data;
      this.result = data.length;
    }, error=>{
      alert(error.error.message)
    })
  }
  
  getIdProduct(item:ProductPayload){
    item.checkselect = !item.checkselect;
    if (item.checkselect == true)
      this.productDelete.push(item)
    else{
      this.productDelete.forEach(function(value, index, object){
        if (value.id == item.id)
            object.splice(index, 1);
      })
    }
  }

  clickDelete(product: ProductPayload){
    if(confirm("Are you sure you want to remove \""+product.name + "\" ?")){
      // tránh trường hợp đã tích chọn select
      this.productDelete = [] 
      this.productDelete.push(product)
      this.delete();
    }
  }
  delete(){
    if (this.productDelete.length == 0){
      return;
    }
    if(this.productDelete.length > 1)
      if(!confirm("Are you sure you want to delete the selected products?")){return}
    
    
    this.http.postRequest("/product/delete/all-by-select", this.productDelete).subscribe(data=>{
      this.products = data;
      this.result = data.length;
    }, error=>{
      alert(error.error.message);
    })
    this.productDelete=[];
  }

  search(){
    if(this.key==null || this.key == ""){
      this.router.navigate(["/product"])
      return
    }
    this.router.navigate(["/product"], {queryParams:{'key': this.key}})
  }

  getSearch(){
    this.http.getRequest("/product/search/admin?key="+this.key).subscribe(data=>{
      this.products = data;
      this.result = data.length;
    })
  }

}
