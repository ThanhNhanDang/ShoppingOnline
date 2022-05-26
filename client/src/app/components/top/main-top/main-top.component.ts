import { Component, OnInit, HostListener } from '@angular/core';

@Component({
  selector: 'app-main-top',
  templateUrl: './main-top.component.html',
  styleUrls: ['./main-top.component.css']
})
export class MainTopComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }
  // nav_variable =false;
  // @HostListener("document:scroll")
  // scrollFunction(){
  //   if(document.body.scrollTop > 0 || document.documentElement.scrollTop > 0)
  //     this.nav_variable = true;
  //   else 
  //     this.nav_variable = false;
  // }
}
