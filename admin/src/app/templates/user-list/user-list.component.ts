import { RolePlayload } from './../payload/RolePayload';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpService } from './../service/httpService/http.service';
import { environment } from './../../../environments/environment';
import { UserProfile } from './../payload/UserProfile';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
  users!: UserProfile[];
  result!: number;
  roles!:RolePlayload[];
  key2 = "Select account";
  baseUrl = environment.urlServe
  key!:string;
  constructor(private http:HttpService, private router:Router, private activatedRouter:ActivatedRoute) {

  }

  ngOnInit(): void {
    this.getAllRole();
    this.activatedRouter.queryParams.subscribe(data=>{
      if(data.key ==null || data.key == ""){
        this.getAllUser();
        return
      }
      else{
        this.key = data.key
        this.getSearch(data.key);
        return;
      }
    })

   
  }
  getAllUser(){
    this.http.getRequest("/user/all").subscribe(data=>{
      this.users = data;
      this.result = data.length;
    }, error=>{
      alert(error.error.message);
    })
  }

  getAllRole(){
    this.http.getRequest("/role/all").subscribe(data=>{
      this.roles = data;
    },error=>{
      alert(error.error.message);
    })
  }
  clickActive(item:UserProfile){
    item.is_email_verfied = !item.is_email_verfied;
    this.http.putRequest("/user/update/active-admin", item).subscribe(data=>{
    }, error=>{
      item.is_email_verfied = !item.is_email_verfied;
      alert(error.error.message)
    })
  }
  search(){
    if(this.key==null || this.key == ""){
      this.router.navigate(["/accounts"])
      return
    }
    this.router.navigate(["/accounts"], {queryParams:{'key': this.key}})
  }

  getSearch(key:string){
    this.http.getRequest("/user/search?key="+key).subscribe(data=>{
      this.users = data;
      this.result = data.length;
    })
  }

  search2(){
   if(this.key2 == "Select account"){
      this.router.navigate(["/accounts"])
      return;
   }
   this.router.navigate(["/accounts"], {queryParams:{'key': this.key2}})
  }

  account(id:number){
    this.router.navigate(["/account"], {queryParams:{'key': id}})
  }

  delete(item:UserProfile){
    if(confirm("Are you sure you want to delete this account?")){
      this.http.deleteRequest(`/user/delete?id=${item.id}&fileId=${item.fileId}`,"").subscribe(data=>{
        this.users = data;
        this.http.deleteRequest(`/upload/delete/${item.fileId}`,"");
      }, error=>{
        alert("Can't delete: "+error.error.message);
      });
    }
  }
}
