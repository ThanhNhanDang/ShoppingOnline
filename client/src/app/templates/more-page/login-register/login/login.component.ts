import { UserProfile } from './../../../../payload/UserProfile';
import { AuthenticationResponse } from 'src/app/payload/AuthenticationResponse';
import { AppCookieService } from './../../../../service/appCookie/app-cookie.service';
import { Router } from '@angular/router';
import { FormGroup, FormControl } from '@angular/forms';
import { HttpServiceService } from './../../../../service/httpService/http-service.service';

import { Component, OnInit } from '@angular/core';
import { FacebookLoginProvider, GoogleLoginProvider, SocialAuthService } from 'angularx-social-login';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  myScriptElement!: HTMLScriptElement;
  loginForm!: FormGroup;
  error!:string;
  constructor(private authService: SocialAuthService, private http: HttpServiceService, private router:Router, private appCookie:AppCookieService) { 
  }

  ngOnInit(): void {
    this.http.setTitle("E Store-Login")
    this.myScriptElement = document.createElement("script");
    this.myScriptElement.src =  "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.2/js/all.min.js";
    document.body.appendChild(this.myScriptElement);
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
    if(request.email == null  || request.email == ""|| request.password == null ||  request.password == "")
      return;
    this.http.loginRequest("/login/user", request).subscribe(data=>{
      this.setLogin(data);
    }, error=>{
      this.error=error.error.message;
    })
  }


  signInWithGoogle(): void {
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID).then(data=>{
      this.http.loginRequest("/login/user/google", {value:data.idToken}).subscribe(data=>{
        this.setLogin(data);
      },error=>{
        this.error=error.error.message;
      });
    });
  }

  signInWithFB(): void {
    this.authService.signIn(FacebookLoginProvider.PROVIDER_ID).then(data=>{
      this.http.loginRequest("/login/user/facebook", {value:data.authToken}).subscribe(data=>{
        this.setLogin(data);
      },error=>{
        this.error=error.error.message;
      });
    });
  }

  setLogin(data:AuthenticationResponse){
    this.appCookie.set("authenticationToken", data.authenticationToken)
    this.http.setLoginData (data.userProfile);
    localStorage.setItem("search key", "");
    this.http.sendClickSubject();
    this.http.sendSubjectBottomBarForLogin();
    this.router.navigateByUrl("/home");
  }

  setUpdate(userProfile:UserProfile){
    this.http.setLoginData (userProfile);
    localStorage.setItem("search key", "");
    alert("Successfully" );
    this.http.sendClickSubject();
  }
}
