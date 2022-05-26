import { AppCookieService } from './templates/service/appCookie/app-cookie.service';
import { HttpService } from './templates/service/httpService/http.service';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CanActiveGuard implements CanActivate {
  constructor(private http: HttpService, private router: Router, private appCookie:AppCookieService){}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let isAuthenticated = this.http.isLogin();
    let isAdmin = this.http.getLoginDataByKey("role_id");
    
    if(isAuthenticated && isAdmin == "1"){//Nếu chưa đăng nhập hoặc quyền là USER
      
      return true;
      
    }
    this.router.navigateByUrl("/login")
    return false;
  }
  
}
