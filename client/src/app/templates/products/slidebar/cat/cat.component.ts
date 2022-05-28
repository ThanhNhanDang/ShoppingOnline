import { Router, ActivatedRoute } from '@angular/router';
import { CategoryPayload } from './../../../../payload/CategoryPayload';
import { Observable } from 'rxjs';
import { HttpServiceService } from './../../../../service/httpService/http-service.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-cat',
  templateUrl: './cat.component.html',
  styleUrls: ['./cat.component.css']
})
export class CatComponent implements OnInit {
  categories !: Observable<Array<CategoryPayload>>;
  selectedItem = 0;
  

  constructor(private activeRouter: ActivatedRoute, private router:Router,private http : HttpServiceService) {}
  ngOnInit(): void {
    this.selectedItem = 0;
    this.activeRouter.queryParams.subscribe(data=>{
      if(data.icate != null|| data.icate != "")
        this.selectedItem = data.icate
    })
    
    this.categories = this.http.getRequest("/category/all", "")
  }
  class(category:CategoryPayload){
   
    this.selectedItem = category.id;
    this.router.navigate(['/products'],{queryParams:{'caterory':category.name,'icate': category.id, 'page':1}});
 
  }
  reset(){
    this.selectedItem = 0;
    this.router.navigateByUrl("/products")
    console.log(this.selectedItem)
  }
}
