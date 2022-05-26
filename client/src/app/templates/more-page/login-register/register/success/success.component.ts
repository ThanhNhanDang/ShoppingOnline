import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.css']
})
export class SuccessComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }
  gmail(){
    window.open( 
      "https://mail.google.com", "_blank");
  }
}
