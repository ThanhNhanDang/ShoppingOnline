import { Title } from '@angular/platform-browser';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})



export class AppComponent {

  constructor(private titleService:Title){
    this.titleService.setTitle("E Store");
    
  }
  active(){
    window.scroll(0,0);
  }
}
