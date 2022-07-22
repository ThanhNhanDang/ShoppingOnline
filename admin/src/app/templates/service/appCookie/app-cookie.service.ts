import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AppCookieService {
  cookieStore!: any;

  constructor() {
    this.parseCookies(document.cookie);
  }

  public parseCookies(cookies = document.cookie) {
    this.cookieStore = {};
    if (!!cookies === false) { return; }
    const cookiesArr = cookies.split(';');
    for (const cookie of cookiesArr) {
      const cookieArr = cookie.split('=');
      this.cookieStore[cookieArr[0].trim()] = cookieArr[1];
    }
  }

  get(key: string) {
    this.parseCookies();
    return !!this.cookieStore[key] ? this.cookieStore[key] : null;
  }

  remove(key: string) {
    document.cookie = `${key} = ;`;
  }

  set(key: string, value: string) {
    var date = new Date();
    var days = 9 // Set cooki trong vong 9 ngay
    date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
    var expires = "; expires=" + date.toUTCString();
    document.cookie = key + '=' + (value || '') + expires + "; path=/";
  }
}
