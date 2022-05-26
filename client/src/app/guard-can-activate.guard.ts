import { Location } from '@angular/common';
import { HttpServiceService } from './service/httpService/http-service.service';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GuardCanActivateGuard implements CanActivate {
  constructor(private http: HttpServiceService, private router: Router, private _location:Location){

  }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let isAuthenticated = this.http.isLogin();
    let isAdmin = this.http.getLoginDataByKey("role_id");

    if(state.url =="/admin"){
      // Đối với url == /admin thì đã đăng nhập hoặc admin mới cho đi tiếp
      if(!isAuthenticated && isAdmin == "1"){
        return true;
      }
      this.router.navigateByUrl("/403-error")
      return false;
    }
     // Đối với url như phía dưới thì chưa đăng nhập mới được vao
    if(state.url =="/register/verify"){
        if(isAuthenticated)
          return true;
        return false;
    }

    if(state.url =="/login" || state.url =="/register" )
      return true;
    
    // Những url còn lại thì đã đăng nhập mới cho qua
    if(!isAuthenticated)
        return true;
    this.http.sendLoginSucject();
    this._location.back();
    return false;
  }
  
  
}

