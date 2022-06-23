import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { AppCookieService } from './../appCookie/app-cookie.service';
import { ProductPayload } from './../../payload/ProductPayload';
import { environment } from './../../../environments/environment';
import { UserProfile } from './../../payload/UserProfile';
import { ResgisterPayload } from './../../payload/registerPayload';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable, Subject, ReplaySubject } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthenticationResponse } from 'src/app/payload/AuthenticationResponse';

@Injectable({
  providedIn: 'root'
})
export class HttpServiceService {
  itemCartWhenDelete = 0;
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };
  message = "";
  baseUrl = environment.baseUrl;
  emailUrl = environment.urlEmail;
  private subjectItemCartWhenDelete = new Subject<any>();
  private subject = new Subject<any>();
  private subjectWishList = new Subject<any>();
  private loginSubject = new Subject<any>();
  private subjectProduct = new Subject<any>();
  private subjectBottomBarForLogin = new Subject<any>();
  private resetBottomBarWhenCheckout = new Subject<any>();
  private subjectItemWhenMiniCartDelete = new Subject<any>();
  private subjectItemWhenMiniWishlistDelete = new Subject<any>();
  private subjectUpdateMiniCart = new Subject<any>();
  constructor(private titleService: Title, private http: HttpClient, private httpClient: HttpClient, private appCookie: AppCookieService, private router: Router) {
  }

  public setTitle(newTitle: string) {
    this.titleService.setTitle(newTitle);
  }
  postRequest(url: String, param: {}) {
    return this.http.post<any>(this.baseUrl + url, param, this.httpOptions)
  }
  postRequestEmail(url: String, param: {}) {
    return this.http.post<any>(this.emailUrl + url, param, this.httpOptions)
  }
  loginRequest(url: String, param: {}): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(this.baseUrl + url, param, this.httpOptions);
  }

  putRequest(url: String, param: {}) {
    return this.http.put<any>(this.baseUrl + url, param, this.httpOptions)
  }
  deleteRequest(url: String, param: {}) {
    return this.http.delete<any>(this.baseUrl + url, param)
  }
  getRequest(url: String, param: {}) {
    return this.http.get<any>(this.baseUrl + url, param)
      .pipe(
        catchError(this.handleError.bind(this))
      );
  }

  getAllProduct(url: String, param: {}) {
    return this.http.get<Array<ProductPayload>>(this.baseUrl + url, param)

  }
  private handleError(error: any): any {
    if (error.error instanceof ErrorEvent) {
      console.error("An error occurred: " + error.error.message);
    }
    else {
      return throwError("Something went wrong.. while connecting with server");
    }
  }
  register(registerPayload: ResgisterPayload) {
    return this.httpClient.post<any>(this.baseUrl + "/signup/user", registerPayload);
  }

  setLoginData(data: UserProfile) {
    localStorage.setItem("login_obj", JSON.stringify(data));
  }

  getLocalStorageItem(key: string) {
    return localStorage.getItem(key) || '{}';
  }
  getLoginDataByKey(key: string) {
    let data = JSON.parse(localStorage.getItem("login_obj") || '{}');
    if (data.hasOwnProperty(key)) {
      return data[key];
    }
    return null;
  }

  sendClickSubject() {
    this.subject.next();
  }
  getSubject(): Observable<any> {
    return this.subject.asObservable();
  }

  sendSubjectWishList() {
    this.subjectWishList.next();
  }
  getSubjectWishList(): Observable<any> {
    return this.subjectWishList.asObservable();
  }
  sendBottomBarWhenCheckout() {
    this.resetBottomBarWhenCheckout.next();
  }
  getBottomBarWhenCheckout(): Observable<any> {
    return this.resetBottomBarWhenCheckout.asObservable();
  }

  sendSubjectItemCartWhenDelete() {
    this.subjectItemCartWhenDelete.next();
  }
  getSubjectItemCartWhenDelete(): Observable<any> {
    return this.subjectItemCartWhenDelete.asObservable();
  }
  sendSubjectBottomBarForLogin() {
    this.subjectBottomBarForLogin.next();
  }
  getSubjectBottomBarForLogin(): Observable<any> {
    return this.subjectBottomBarForLogin.asObservable();
  }
  sendClickSubjecProduct() {
    this.subjectProduct.next();
  }
  getSubjectProduct(): Observable<any> {
    return this.subjectProduct.asObservable();
  }

  getLoginToken() {
    return this.appCookie.get("authenticationToken")
  }
  logout() {
    this.appCookie.remove("authenticationToken")
    localStorage.setItem("login_obj", "");
    localStorage.setItem("productId", "");
    localStorage.setItem("search key", "");
    this.sendSubjectBottomBarForLogin();
  }
  isLogin() {
    if (this.getLoginToken() != "" && String(this.getLoginToken())?.length > 10) {
      return false
    }
    return true;
  }

  setItemCartWhenDelete(itemCartWhenDelete: number) {
    this.itemCartWhenDelete = itemCartWhenDelete;
  }

  getItemCartWhenDelete() {
    return this.itemCartWhenDelete;
  }

  sendSubjectItemWhenMiniCartDelete() {
    this.subjectItemWhenMiniCartDelete.next();
  }
  getSubjectItemWhenMiniCartDelete(): Observable<any> {
    return this.subjectItemWhenMiniCartDelete.asObservable();
  }

  sendSubjectItemWhenMiniWishlistDelete() {
    this.subjectItemWhenMiniWishlistDelete.next();
  }
  getSubjectItemWhenMiniWishlistDelete(): Observable<any> {
    return this.subjectItemWhenMiniWishlistDelete.asObservable();
  }
  sendSubjectUpdateMiniCart() {
    this.subjectUpdateMiniCart.next();
  }
  getSubjectUpdateMiniCart(): Observable<any> {
    return this.subjectUpdateMiniCart.asObservable();
  }

  clickDetailProduct(productId: string) {
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigate(['/product-detail'], { queryParams: { 'key': productId } });
    });
  }

  pushFileToStorage(url: string, file: File) {
    const formdata: FormData = new FormData();
    formdata.append('file', file);
    return this.http.post(this.baseUrl + url, formdata)
  }
  updateFileToStorage(url: string, file: File) {
    const formdata: FormData = new FormData();
    formdata.append('file', file);
    return this.http.put(this.baseUrl + url, formdata)
  }

  sendLoginSucject() {
    this.loginSubject.next();
  }
  getLoginSucject() {
    return this.loginSubject.asObservable();
  }

  checkLogin() {
    if (this.isLogin() == true) {
      this.sendLoginSucject();
      return false;
    }
    return true;
  }
  setUpdate(userProfile: UserProfile) {
    this.setLoginData(userProfile);
    localStorage.setItem("search key", "");
    this.sendClickSubject();
  }

  getRoundingNumber(input: number) {
    return Number((Math.round(input * 100) / 100).toFixed(2));
  }
}


