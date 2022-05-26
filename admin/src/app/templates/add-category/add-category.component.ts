import { HttpService } from './../service/httpService/http.service';
import { CategoryPayload } from './../payload/CategoryPayload';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.scss']
})
export class AddCategoryComponent implements OnInit {
  categories !: CategoryPayload[];
  category = new CategoryPayload("", "fa fa-female");
  constructor(private http : HttpService) { }

  ngOnInit(): void {
    this.http.getRequest("/category/all").subscribe(data=>{
      this.categories = data;
    },error=>{
      alert(error.error.message)
    })
  }

  addNow(){
    if(this.category.name == "" || this.category.classFa == ""){
      alert("Invalid category")
      return;
    }

    this.http.postRequest("/category/add", this.category).subscribe(data=>{
      this.categories.push(data);
      this.category.name = "";
      alert("Successful")
    })
    
  }
  delete(item:CategoryPayload){
    if(confirm("Are you sure you want to remove \""+item.name + "\" ?")){
      this.http.deleteRequest("/category/delete/" + item.id, "").subscribe(data=>{
        this.categories = data;
        alert("Delete category successfully")
      }, error=>{
        alert(error.error.message)
      })
    }
  }

}
