import { SearchAndSortPayload } from './../../../../payload/SearchAndSortPayload';
import { Router } from '@angular/router';
import { environment } from './../../../../../environments/environment';
import { ProductPayload } from './../../../../payload/ProductPayload';
import { HttpServiceService } from './../../../../service/httpService/http-service.service';
import { Component, OnInit } from '@angular/core';
@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  products!:ProductPayload[];
  checkSearch= false;
  baseUrl= environment.urlServe;
  totalElements = 0;
  searchAndSort = new SearchAndSortPayload();
  constructor(private http :HttpServiceService, private router:Router) { }

  ngOnInit(): void {
  }
  search(){
    if(this.searchAndSort.key.length < 3){
      this.products = []
      this.checkSearch = false
      return;
    }
    this.http.postRequest("/product/search",this.searchAndSort).subscribe(data=>{
      if (data.totalElements == 0){
        this.checkSearch = false;
        return;
      }
      this.products=data.content;
      this.totalElements = data.totalElements
      this.checkSearch = true
    })

  }
  clickDetailProduct(productId:string){
    this.checkSearch = false
    this.products = []
    this.http.clickDetailProduct(productId);
  }

  productSearch(){
    if(this.searchAndSort.key.length < 3){
      alert("Keyword too short.")
      return;
    }
    this.products = []
    this.checkSearch = false
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
    this.router.navigate(['/products'], {queryParams:{'key':this.searchAndSort.key}});
    })
  }
}
