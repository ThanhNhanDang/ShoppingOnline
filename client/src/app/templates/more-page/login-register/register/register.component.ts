import { FacebookLoginProvider, GoogleLoginProvider, SocialAuthService } from 'angularx-social-login';
import { Gender } from './../../../../payload/Gender';
import { HttpServiceService } from './../../../../service/httpService/http-service.service';
import { ResgisterPayload } from '../../../../payload/registerPayload';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  urlVerify = "https://thanhnhandev.xyz";
  myScriptElement!: HTMLScriptElement;
  form!: FormGroup;
  registerPayload!: ResgisterPayload;
  selected = "Male"
  genders: Gender[] = [
    { value: 'Male' },
    { value: 'Female' },
    { value: 'Prefer not to say' }
  ];
  constructor(private authService: SocialAuthService, private formBuilder: FormBuilder, private httpService: HttpServiceService, private router: Router) {

  }
  ngOnInit(): void {
    this.httpService.setTitle("E Store-Register")
    this.myScriptElement = document.createElement("script");
    this.myScriptElement.src = "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.2/js/all.min.js";
    document.body.appendChild(this.myScriptElement);
    this.registerPayload = {
      name: '',
      email: '',
      mobile: '',
      password: '',
      gender: '',
      confirmPassword: '',
    };
    this.form = this.formBuilder.group({
      firstName: new FormControl(null, Validators.required),
      lastName: new FormControl(null, Validators.required),
      emailAddress: new FormControl(null, [Validators.required, Validators.email]),
      mobileNumber: new FormControl(null,
        [
          Validators.required,
          Validators.pattern("^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$")
        ]),
      password: new FormControl(null, [Validators.required, Validators.minLength(6)]),
      confirmPassword: new FormControl(null),
      genderSelect: new FormControl(),
    },
      {
        validators: this.MustMatch("password", "confirmPassword")
      })
  }
  clickSub() {
    this.registerPayload.name = this.form.get('firstName')?.value + " " + this.form.get("lastName")?.value;
    this.registerPayload.email = this.form.get("emailAddress")?.value;
    this.registerPayload.password = this.form.get("password")?.value;
    this.registerPayload.mobile = this.form.get("mobileNumber")?.value;
    this.registerPayload.gender = this.form.get("genderSelect")?.value;
    this.httpService.register(this.registerPayload).subscribe(data => {
      console.log(data)
      const url = `${this.urlVerify}/register/verify/active?email=${data.email}&code=${data.login_token}`
      const email = {
        from: "Account verification",
        to: data.email,
        subject: "Please verify your registration",
        htmlContent:
          `<b><p>Dear ${data.name},</p></b></br>
          <p>Please click the link below to verify to your registration: </p>
          <h3><a href=${url}>VERIFY</a></h3>
          <p>Thank you<br>The ESTORE</p>`
      }
      this.httpService.postRequestEmail(`/doan1/email/send/estore`, email).subscribe(() => {
        alert("Registration Succses");
        this.router.navigateByUrl("/register/verify");
      }, error => {
        console.log(error)
        alert(error);
        return;
      })
    }, error => {
      alert(error.error.message);
    });

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

  signInWithGoogle(): void {
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID).then(data => {
      console.log(data)
      this.httpService.postRequest("/signup/user/google", { value: data.idToken }).subscribe(data => {
        alert("Registration Succses")
        this.router.navigateByUrl("/login");
      }, error => {
        alert(error.error.message)
      });
    });
  }

  signInWithFB(): void {
    this.authService.signIn(FacebookLoginProvider.PROVIDER_ID).then(data => {
      this.httpService.postRequest("/signup/user/facebook", { value: data.authToken }).subscribe(data => {
        alert("Registration Succses")
        this.router.navigateByUrl("/register/verify");
      }, error => {
        alert(error.error.message)
      });
    });
  }
}
