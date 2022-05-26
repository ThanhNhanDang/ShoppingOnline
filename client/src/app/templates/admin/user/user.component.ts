import { environment } from './../../../../environments/environment';
import { UserProfile } from './../../../payload/UserProfile';
import { HttpServiceService } from './../../../service/httpService/http-service.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  users!: UserProfile[]
  baseUrl = environment.urlServe
  constructor(private http:HttpServiceService) {
  
  }

  ngOnInit(): void {
    this.http.getRequest("/user/all","").subscribe(data=>{
      this.users = data;
    }, error=>{
      alert(error.error.message);
    })
  }

}
