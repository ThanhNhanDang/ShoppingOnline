import { DistrictPayload } from './../../payload/DistrictPayload';
import { ProvincePayload } from './../../payload/ProvincePayload';
import { HttpService } from './../../service/httpService/http.service';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-update-district',
  templateUrl: './update-district.component.html',
  styleUrls: ['./update-district.component.scss']
})
export class UpdateDistrictComponent implements OnInit {
  district!: DistrictPayload;
  provinces: ProvincePayload[] = [];
  districtId!: string;
  constructor(private activatedRouter: ActivatedRoute, private http: HttpService, private _location: Location) { }

  ngOnInit(): void {
    this.activatedRouter.queryParams.subscribe(data => {
      if (data.districtId == null || data.districtId == "") {
        return;
      }
      else {
        this.districtId = data.districtId;
        this.getDistrict();
        this.getAllProvince();
      }
    })
    
  }

  public update() {
    this.http.putRequest("/Vietnamese-Administrative-Unit/districts/update", this.district).subscribe(() => {
      alert("Successful")
      this._location.back();
    }, error => {
      alert(error.error.message)
    })
  }
  public getDistrict() {
    this.http.postRequest("/Vietnamese-Administrative-Unit/districts/by-id", { districtId: this.districtId }).subscribe(data => {
      this.district = data;
    }, error => {
      alert(error.error.message)
      this._location.back();
    })
  }

  getAllProvince() {
    this.http.getRequest("/Vietnamese-Administrative-Unit/provinces/get-all").subscribe(data => {
      this.provinces = data;
    }, error => {
      alert(error.error.message);
    })
  }

}
