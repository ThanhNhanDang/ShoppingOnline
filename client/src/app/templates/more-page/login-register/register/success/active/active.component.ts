import { Router, ActivatedRoute } from '@angular/router';
import { HttpServiceService } from './../../../../../../service/httpService/http-service.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-active',
  templateUrl: './active.component.html',
  styleUrls: ['./active.component.css']
})
export class ActiveComponent implements OnInit {
  checkIsEmail = false;
  error!:string
  constructor(private http:HttpServiceService, private router:Router, private activatedRoute:ActivatedRoute) { }
  
  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(data=>{
      if (data.code.length < 12 || data.email.length<12){
        this.router.navigateByUrl("/register/verify");
        return;
      }
      this.verify(data.email,data.code);
    })
  }

  verify(email:string, code:string){
    this.http.postRequest("/user/signup/active?email="+email+"&code="+code,"").subscribe(()=>{
      this.checkIsEmail = true;
    }, error=>{
      this.checkIsEmail = false;
      this.error = error.error.message;
    })
  }
}
