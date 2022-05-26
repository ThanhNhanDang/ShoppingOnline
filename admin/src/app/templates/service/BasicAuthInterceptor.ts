import { SpinnerService } from './spinnerService/spinner.service';
import { AppCookieService } from './appCookie/app-cookie.service';
import { Observable } from 'rxjs';
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs/operators'
@Injectable()
export class BasicAuthInterceptor implements HttpInterceptor {
    constructor(private appCookie:AppCookieService, private spinnerSerivce:SpinnerService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // add authorization header with basic auth credentials if available
        
        if(!request.url.includes("search"))
            this.spinnerSerivce.requestStarted();
        const token = this.appCookie.get("authenticationToken");
        if (!token) {
            return this.handler(request, next);
        }
        const req1 = request.clone({
            headers: request.headers.set('Authorization', "Bearer " + token),
          });
        return this.handler(req1, next);
    }

    handler(request:any, next: HttpHandler){
        return next.handle(request)
        .pipe(
            tap(
                (event)=>{
                    if(event instanceof HttpResponse){
                        this.spinnerSerivce.requestEnded();
                    }
                },
                (error: HttpErrorResponse)=>{
                    this.spinnerSerivce.resetSpinner();
                    throw error;
                }
            )
        );
    }
}