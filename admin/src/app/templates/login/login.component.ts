import { AppCookieService } from './../service/appCookie/app-cookie.service';
import { HttpService } from './../service/httpService/http.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  constructor(private http: HttpService, private router:Router, private appCookie:AppCookieService) { }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      email : new FormControl(),
      password: new FormControl()
    })
  }
  loginUserCheck(){
    let request={
        "email":this.loginForm.get("email")?.value ,
        "password": this.loginForm.get("password")?.value
    }
    this.http.postRequest("/login/user", request).subscribe(data=>{
      
      if(data.hasOwnProperty('authenticationToken')){
        if(data['userProfile'].role_id != 1){
          return alert("Login failed");
        }
        this.appCookie.set("authenticationToken", data['authenticationToken'])
        this.http.setLoginData (data['userProfile']);
        alert("Successfully");
        this.router.navigateByUrl("/dashboard");
        this.http.sendSubjectLogin();
      }
    }, error=>{
      alert("Error in login " + error.error.message);
    })
  }

}
