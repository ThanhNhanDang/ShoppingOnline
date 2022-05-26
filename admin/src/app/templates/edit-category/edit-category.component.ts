import {Location} from '@angular/common';
import { CategoryPayload } from './../payload/CategoryPayload';
import { HttpService } from './../service/httpService/http.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-edit-category',
  templateUrl: './edit-category.component.html',
  styleUrls: ['./edit-category.component.scss']
})
export class EditCategoryComponent implements OnInit {
  categories !: CategoryPayload[]
  constructor(private http: HttpService, private _location: Location) { }

  ngOnInit(): void {
    this.http.getRequest("/category/all").subscribe(data=>{
      this.categories = data;
    },error=>{
      alert(error.error.message)
    })
  }
  update(){
    this.http.putRequest("/category/update-all-category", this.categories).subscribe(()=>{
      alert("Successful");
      this._location.back();
    }, error=>{
      alert(error.error.message);
    })
  }
  
}
