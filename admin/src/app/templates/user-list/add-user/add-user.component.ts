import { Router } from '@angular/router';
import { UserAdmin } from './../../payload/UserAddAdmin';
import { HttpService } from './../../service/httpService/http.service';
import { Gender } from './../../payload/Gender';
import { environment } from './../../../../environments/environment';
import { RolePlayload } from './../../payload/RolePayload';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent implements OnInit {
  roles!:RolePlayload[];
  active =[{value:true, status:"Active"}, {value:false, status:'Inactive'}]

  baseUrl = environment.urlServe;
  user = new UserAdmin('','', '', '', "Male", 2, false);
  imgURL!:string;
  genders: Gender[] = [
    {id:1,value: 'Male'},
    {id:2,value: 'Female'},
    {id:3,value: 'Prefer not to say'}
  ];

  key!:string
  constructor(private router: Router, private http : HttpService) { }
  
  ngOnInit(): void {
    this.imgURL = this.baseUrl+"/"+"userDefault.png";
    this.getAllRole();
  }

  addNow(){
    this.http.postRequest("/user/add/by-admin",this.user).subscribe(()=>{
      alert("Successful.")
      this.router.navigateByUrl("/accounts")
    }, error=>{
      alert(error.error.message)
    }) 
  }
  getAllRole(){
    this.http.getRequest("/role/all").subscribe(data=>{
      this.roles = data;
    },error=>{
      alert(error.error.message);
    })
  }
}
