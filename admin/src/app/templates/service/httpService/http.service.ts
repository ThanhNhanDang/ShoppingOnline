import { AppCookieService } from './../appCookie/app-cookie.service';
import { UserProfile } from './../../payload/UserProfile';
import { environment } from './../../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HttpService {
  baseUrl = environment.baseUrl;
  private subjectLogin = new Subject<any>();

  constructor(private http: HttpClient, private appCookie: AppCookieService) { }

  postRequest(url: String, param: {}) {
    return this.http.post<any>(this.baseUrl + url, param)

  }
  postRequestFileToStorage(url: String, param: {}, file: File) {
    const formdata: FormData = new FormData();
    formdata.append('file', file);
    formdata.append('dto', JSON.stringify(param));
    return this.http.post<any>(this.baseUrl + url, formdata)
  }
  postRequestMulFileToStorage(url: String, param: {}, files: File[]) {
    const formdata: FormData = new FormData();
    files.map(item => formdata.append('file', item));
    formdata.append('dto', JSON.stringify(param));
    return this.http.post<any>(this.baseUrl + url, formdata)
  }

  putRequest(url: String, param: {}) {

    return this.http.put(this.baseUrl + url, param)
  }
  putRequestFileToStorage(url: String, param: {}, file: File) {
    const formdata: FormData = new FormData();
    formdata.append('file', file);
    formdata.append('dto', JSON.stringify(param));
    return this.http.put<any>(this.baseUrl + url, formdata)
  }
  putRequestMulFileToStorage(url: String, param: {}, files: File[]) {
    const formdata: FormData = new FormData();
    files.map(item => formdata.append('file', item));
    formdata.append('dto', JSON.stringify(param));
    return this.http.put<any>(this.baseUrl + url, formdata)
  }
  getRequest(url: String) {
    return this.http.get<any>(this.baseUrl + url)
  }
  deleteRequest(url: String, param: {}) {
    return this.http.delete<any>(this.baseUrl + url, param)
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

  setLoginData(data: UserProfile) {
    localStorage.setItem("login_obj", JSON.stringify(data));
  }
  logout() {
    this.appCookie.remove("authenticationToken")
    localStorage.setItem("login_obj", "");

  }
  isLogin() {
    if (this.appCookie.get("authenticationToken") != "" && String(this.appCookie.get("authenticationToken"))?.length > 10)
      return true
    return false;
  }
  getLoginDataByKey(key: string) {
    let data = JSON.parse(localStorage.getItem("login_obj") || '{}');
    if (data.hasOwnProperty(key)) {
      return data[key];
    }
    return null;
  }


  sendSubjectLogin() {
    this.subjectLogin.next();
  }
  getSubjectLogin(): Observable<any> {
    return this.subjectLogin.asObservable();
  }
  getRoundingNumber(input: number) {
    return Number((Math.round(input * 100) / 100).toFixed(2));
  }

}
