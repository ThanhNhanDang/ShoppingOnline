import { Component } from '@angular/core';
import {Location} from '@angular/common';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'admin';
  isLoading = true;
  constructor(private _location: Location){}
  active(){
    window.scroll(0,0);
  }
  backClicked() {
    this._location.back();
  }
}
