import { SearchAndSortPayload } from './../../payload/SearchAndSortPayload';
import { environment } from './../../../environments/environment';
import { ProductService } from './../../service/product/product.service';
import { ProductPayload } from './../../payload/ProductPayload';
import { Subscription } from 'rxjs';
import { HttpServiceService } from './../../service/httpService/http-service.service';
import { Component, OnInit } from '@angular/core';
import { Location} from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';

interface sortItem{
  id:number;
  name:string;
  value:string;
  oldOrNew:boolean;
}

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})

export class ProductsComponent implements OnInit {
  searAndSort!:SearchAndSortPayload;
  products!: ProductPayload[];
  lastPage!:number;
  totalElements = 0;
  active = false;

  cateName="";
  

  //sort
  sortKey="Newest";
  sortId = 1;
  sortKeys:sortItem[] =[
    {id: 1, name:"Newest", value:"id", oldOrNew: true},
    {id:2, name:"Oldest", value:"id",oldOrNew: false},
    {id: 3, name:"Popular", value:"totalReview5Star",oldOrNew: true},
    {id: 4, name:"Most sale", value:"unitSold",oldOrNew: true}
  ]
  checkSort=false;
  //sort
  //page
  previousNext !:number;
  selectedItem !:number;
  //end page

  changeProductByCategory! : Subscription;
  baseUrl = environment.urlServe;
  j = 0;
  
  constructor(private router:Router,private activatedRouter:ActivatedRoute,private _location: Location,public http: HttpServiceService, private productService:ProductService){}
  ngOnInit(): void {
    this.previousNext = this.selectedItem = 1
    this.activatedRouter.queryParams.subscribe(data=>{
      this.searAndSort = new SearchAndSortPayload();
      if(data.key != null){
        this.searAndSort.key = data.key;
      }
      if(data.value != null)
        this.searAndSort.sortKey = data.value;
      else{
        this.searAndSort.sortKey = "id";
        this.sortKey="Newest";
        this.sortId = 1;
      }
        
      if(data.icate != null){
        this.searAndSort.cateId = data.icate;
      }
      if(data.page != null && data.page > 0){
        this.previousNext = this.selectedItem =data.page;
      }
      if(data.newest != null){
        this.searAndSort.oldOrNew =data.newest;
      }
      this.searAndSort.page = this.previousNext -1
      this.getPage();
      if(data.caterory != null){
        this.cateName = data.caterory; 
        this.active = true;
      }
      else
      this.active = false;
    })
    this.http.setTitle("E Store-Products")

  }
  clickCartPlus(productPayload:ProductPayload){
    if(!this.http.checkLogin())
      return 
   this.productService.addCart(productPayload, 1)
  }

  clickProductDetail(productId:string){
    this.http.clickDetailProduct(productId);
  }
  clickWishListPlus(item:ProductPayload){
    if(!this.http.checkLogin())
      return 
   this.productService.addWishList(item)
  }

  getPage(){
    this.http.postRequest("/product/search",this.searAndSort).subscribe(data=>{
      this.totalElements = data.totalElements;
      this.products=data.content;
      this.lastPage = data.totalPages;
      },error=>{
        alert("Server connection error")
    })
    window.scroll(0,0);
  }

  clickPage(page:number){
    this.previousNext = page;
    this.page() 
  }
  page(){
    this.selectedItem = this.previousNext;
    this.searAndSort.page = this.previousNext -1
    this.router.navigate(['/products'], { queryParamsHandling:"merge",queryParams:{'page':this.previousNext}});
    this.getPage();
  }
  nextPage(){
    this.previousNext++;
    if (this.previousNext > this.lastPage){
      this.previousNext = this.lastPage;
      return;
    }
    
    this.page()
  }

  previousPage(){
    this.previousNext--
    if (this.previousNext < 1){
      this.previousNext = 1;
      return;
    }
   this.page() 
  }


  backClicked() {
    this._location.back();
  }
  
  sort(item:sortItem){
    this.sortId = item.id;
    this.sortKey = item.name;
    this.reset()
    this.router.navigate(['/products'], { queryParamsHandling:"merge",queryParams:{'page':this.previousNext, 'value': item.value, 'newest':item.oldOrNew}});
  }

  search(){
    this.reset()
    if(this.searAndSort.key.length < 3){
      alert("Keyword too short.")
      return;
    }
      
    this.router.navigate(['/products'], { queryParamsHandling:"merge",queryParams:{'page':this.previousNext, 'key': this.searAndSort.key}});
  }

  reset(){
    this.previousNext =  this.selectedItem = 1;
  }
}
