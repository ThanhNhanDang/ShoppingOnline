import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { HttpServiceService } from './../../../service/httpService/http-service.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['../login-register/register/register.component.css']
})
export class ResetPasswordComponent implements OnInit {
  checkToken = false;
  token !: string;
  error!: string;
  form!: FormGroup;
  constructor(private formBuilder: FormBuilder, private http: HttpServiceService, private router: Router, private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(data => {
      if (data.token.length < 15) {
        this.router.navigateByUrl("/forgot-password");
        return;
      }
      this.token = data.token;
      this.verify(data.token);
    })
    this.form = this.formBuilder.group({
      password: new FormControl(null, [Validators.required, Validators.minLength(6)]),
      confirmPassword: new FormControl(null),
    }, {
      validators: this.MustMatch("password", "confirmPassword")
    })
  }

  get f() { return this.form.controls }
  MustMatch(controlName: string, matchingControlName: string) {
    return (formGroup: FormGroup) => {
      const control = formGroup.controls[controlName];
      const matching = formGroup.controls[matchingControlName];
      if (control.errors && !matching.errors?.MustMatch) {
        return
      }
      if (control.value !== matching.value) {
        matching.setErrors({ MustMatch: true });
      }
      else {
        matching.setErrors(null);
      }
    }
  }
  verify(token: string) {
    this.http.getRequest("/user/validate-token-password?token=" + token, "").subscribe(data => {
      if (data.message != null) {
        alert(data.message);
        this.router.navigateByUrl("/home");
      }
    }, error => {
      this.checkToken = false;
      this.error = error.error.message;
      this.router.navigateByUrl("/home");
    })
  }

  updatePassword() {

    this.http.putRequest("/user/reset-password", { token: this.token, newPassword: this.form.get("confirmPassword")?.value }).subscribe(data => {
      if (data.error != null) {
        alert(data.error)
        this.router.navigateByUrl("/home");
      }
      else {
        alert(data.message + " Login now.")
        this.router.navigateByUrl("/login");
      };

    })

  }

}
