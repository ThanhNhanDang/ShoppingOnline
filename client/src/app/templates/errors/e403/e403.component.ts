import { Component, OnInit } from '@angular/core';
import {Location} from '@angular/common';
@Component({
  selector: 'app-e403',
  templateUrl: './e403.component.html',
  styleUrls: ['./e403.component.css']
})
export class E403Component implements OnInit {

  constructor(private _location : Location) { }

  ngOnInit(): void {
  }
  backClicked() {
    this._location.back();
  }
}
