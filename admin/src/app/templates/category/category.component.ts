import { HttpService } from './../service/httpService/http.service';
import { CategoryPayload } from './../payload/CategoryPayload';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit {
  categories !: CategoryPayload[]
  
  constructor(private http : HttpService) { }

  ngOnInit(): void {
    this.http.getRequest("/category/all").subscribe(data=>{
      this.categories = data;
    },error=>{
      alert(error.error.message)
    })
  }

  delete(item:CategoryPayload){
    if(confirm("Are you sure you want to remove \""+item.name + "\" ?")){
      this.http.deleteRequest("/category/delete/" + item.id, "").subscribe(data=>{
        this.categories = data;
      }, error=>{
        alert(error.error.message)
      })
    }
  }
  
}
