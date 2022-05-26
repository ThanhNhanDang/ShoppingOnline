import { HttpService } from './../../service/httpService/http.service';
import { ActivatedRoute } from '@angular/router';
import { ProvincePayload } from './../../payload/ProvincePayload';
import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-update',
  templateUrl: './update.component.html',
  styleUrls: ['./update.component.scss']
})
export class UpdateComponent implements OnInit {
  province!: ProvincePayload;
  provinceId!: string;
  constructor(private activatedRouter: ActivatedRoute, private http: HttpService, private _location: Location) { }

  ngOnInit(): void {
    this.activatedRouter.queryParams.subscribe(data => {
      if (data.provinceId == null || data.provinceId == "") {
        return;
      }
      else {
        this.provinceId = data.provinceId;
        this.getProvince();
      }
    })
  }

  public update() {
    this.http.putRequest("/Vietnamese-Administrative-Unit/provinces/update", this.province).subscribe(() => {
      alert("Successful")
      this._location.back();
    }, error => {
      alert(error.error.message)
    })
  }
  public getProvince() {
    this.http.postRequest("/Vietnamese-Administrative-Unit/provinces/by-id", { provinceId: this.provinceId }).subscribe(data => {
      this.province = data;
    }, error => {
      alert(error.error.message)
      this._location.back();
    })
  }
}
