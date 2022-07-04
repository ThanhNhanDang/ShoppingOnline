import { environment } from './../../../../environments/environment';
import { HttpServiceService } from './../../../service/httpService/http-service.service';
import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {
  changeUserName!: Subscription;
  loginSubject!: Subscription;
  name = "";
  avatar = "";
  email = "";
  admin = false;
  baseUrl = environment.urlServe;
  constructor(public http: HttpServiceService) {
  }
  ngOnInit(): void {
    this.name = this.http.getLoginDataByKey("name");
    this.avatar = "/" + this.http.getLoginDataByKey("image_url");
    this.email = this.http.getLoginDataByKey("email");
    if (this.http.getLoginDataByKey("role_id") == "1")
      this.admin = true;
    else this.admin = false;
    this.changeUserName = this.http.getSubject().subscribe(() => {
      this.changeUserNameSubscription()
    })
    this.loginSubject = this.http.getLoginSucject().subscribe(() => {
      alert("Login to continue!")
    })

  }

  logout() {
    this.http.logout();
  }
  changeUserNameSubscription() {
    this.name = this.http.getLoginDataByKey("name");
    this.avatar = '/' + this.http.getLoginDataByKey("image_url");
    this.email = this.http.getLoginDataByKey("email");
    if (this.http.getLoginDataByKey("role_id") == "1")
      this.admin = true;
    else this.admin = false;
  }
}
