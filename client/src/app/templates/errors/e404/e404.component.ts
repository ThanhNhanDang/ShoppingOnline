import { HttpServiceService } from './../../../service/httpService/http-service.service';
import { Component, OnInit } from '@angular/core';
import {Location} from '@angular/common';
@Component({
  selector: 'app-e404',
  templateUrl: './e404.component.html',
  styleUrls: ['./e404.component.css']
})
export class E404Component implements OnInit {

  constructor(private http:HttpServiceService, private _location : Location)  {
    this.http.setTitle("E Store-404 error")
   }

  ngOnInit(): void {
  }
  backClicked() {
    this._location.back();
  }
}
