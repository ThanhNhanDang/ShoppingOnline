import { AuthenticationResponse } from 'src/app/payload/AuthenticationResponse';
import { AppCookieService } from './../../../service/appCookie/app-cookie.service';
import { HttpServiceService } from './../../../service/httpService/http-service.service';
import { Router } from '@angular/router';
import { FormGroup, FormControl } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['../login-register/login/login.component.css']
})
export class ForgotPasswordComponent implements OnInit {
  loginForm!: FormGroup;
  error!: string;
  constructor(private http: HttpServiceService, private router: Router, private appCookie: AppCookieService) { }

  ngOnInit(): void {
    this.http.setTitle("E Store-forgot-password");
    this.http.logout();
    this.loginForm = new FormGroup({
      email: new FormControl(),
    })
  }
  loginUserCheck() {
    let request = {
      "email": this.loginForm.get("email")?.value,
    }
    if (request.email == null || request.email == "")
      return;
    this.http.postRequest("/user/reset-password?email=" + request.email, "").subscribe(data => {
      alert("You HAVE 5 MINUTES to verify your email to reset your password before it expires.")
      this.router.navigateByUrl("/login");
    }, error => {
      this.error = error.error.message;
    })
  }


}
