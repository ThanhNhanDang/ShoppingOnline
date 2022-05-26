import { HttpServiceService } from './../../../service/httpService/http-service.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-main-body',
  templateUrl: './main-body.component.html',
  styleUrls: ['./main-body.component.css']
})
export class MainBodyComponent implements OnInit {

  constructor(private http:HttpServiceService) {
    this.http.setTitle("E Store-Home")
   }

  ngOnInit(): void {
  }

}
