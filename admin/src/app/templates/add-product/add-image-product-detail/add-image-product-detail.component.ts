import { ProductDetailUrlImg } from './../../payload/ProductDetailPayload';
import { HttpService } from './../../service/httpService/http.service';
import { environment } from './../../../../environments/environment';
import { Component, OnInit, ElementRef } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-add-image-product-detail',
  templateUrl: './add-image-product-detail.component.html',
  styleUrls: ['./add-image-product-detail.component.scss']
})
export class AddImageProductDetailComponent implements OnInit {
  productId!:string;
  cateId!:string;
  productDetailUrlImg!:ProductDetailUrlImg[];
  detail!:ProductDetailUrlImg;

  selectedFile!:File;
  baseUrl = environment.urlServe;
  date:any;
  latest_date:any;
  test!:string
  fileName!:string;
  imgURL=this.baseUrl+"images/avatars/userDefault.png"
  constructor(private activatedRouter:ActivatedRoute, private http : HttpService, private _location: Location) { }

  ngOnInit(): void {
    this.activatedRouter.queryParams.subscribe(data=>{
      if(data.product ==null || data.product == "" || data.category ==null || data.category == ""){
        return;
      }
      else{
        this.productId = data.product;
        this.cateId = data.category;
        this.getDetailUrlImg();
      }
    })
  }
  
  onFileChanged(files:any, item:ProductDetailUrlImg, indexOfelement:number){
    if (files.length === 0)
      return;
    item.file = files.item(0)
    this.fileName = item.file.name;
    var mimeType;
    mimeType = files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      alert("Only images are supported.")
      return;
    }
    var reader = new FileReader();
    reader.readAsDataURL(files[0]); 
    reader.onload = (_event) => { 
      item.urlImg = "images/products/"+this.cateId+"/"+this.productId+"/"+this.fileName;
      item.urlImgTemp = reader.result as string;
    }
  }

  getDetailUrlImg(){
    this.http.postRequest("/product-detail/get-product-detail-by-product",{productId: this.productId, cateId:this.cateId}).subscribe(data=>{
      this.productDetailUrlImg = data;
      this.productDetailUrlImg.forEach(value=>{
        value.urlImgTemp = this.baseUrl+ value.urlImg;
      })
    },error=>{
      alert(error.error.message)
      this._location.back();
    })
  }

  addNewImageDetail(){
    this.detail = new ProductDetailUrlImg(this.productId, this.imgURL);
    this.detail.urlImg = "images/avatars/userDefault.png";
    this.http.postRequest("/product-detail/add", this.detail).subscribe(data=>{
      this.detail.id = data.id;
      this.productDetailUrlImg.unshift(this.detail);
    },error=>{
      
      alert(error.error.message);
    })
  }
  
  deleteNow(indexOfelement:number, item:ProductDetailUrlImg){
    if(item.file != null)
      item.urlImg = "images/products/"+this.cateId+"/"+this.productId+"/"+this.fileName;
    this.http.deleteRequest("/product-detail/delete?cateId="+this.cateId+"&id="+item.id+"&productId="+item.productId+"&urlImg="+item.urlImg,"").subscribe(()=>{
      this.productDetailUrlImg.forEach(function(item, index, object) {
        if (index === indexOfelement) {
          object.splice(index, 1);
        }
      });
      alert("Delete successful")
    },error=>{
      alert(error.error.message)
    })
    

  }
  updateNow(item:ProductDetailUrlImg){
    if(item.file!=null){
      this.http.pushFileToStorage("/product-detail/update/image?cateId="+this.cateId+"&productId="+this.productId, item.file).subscribe(()=>{
        this.http.putRequest("/product-detail/update", item).subscribe(()=>{
          alert("Update successful")
        },error=>{
          alert(error.error.message);
        })
      }, error=>{
      alert(error.error.message);
      })

    }   
  }
}
