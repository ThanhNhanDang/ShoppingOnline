import { HttpService } from './../../service/httpService/http.service';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { DistrictPayload } from '../../payload/DistrictPayload';
import { WardPayload } from '../../payload/WardPayload';
import { Location } from '@angular/common';

@Component({
  selector: 'app-update-ward',
  templateUrl: './update-ward.component.html',
  styleUrls: ['./update-ward.component.scss']
})
export class UpdateWardComponent implements OnInit {

  ward!: WardPayload;
  districts: DistrictPayload[] = [];
  wardId!: string;
  constructor(private activatedRouter: ActivatedRoute, private http: HttpService, private _location: Location) { }

  ngOnInit(): void {
    this.activatedRouter.queryParams.subscribe(data => {
      if (data.wardId == null || data.wardId == "") {
        return;
      }
      else {
        this.wardId = data.wardId;
        this.getWard();
        this.getAllDistrict();
      }
    })
    
  }

  public update() {
    this.http.putRequest("/Vietnamese-Administrative-Unit/wards/update", this.ward).subscribe(() => {
      alert("Successful")
      this._location.back();
    }, error => {
      alert(error.error.message)
    })
  }
  public getWard() {
    this.http.postRequest("/Vietnamese-Administrative-Unit/wards/by-id", { wardId: this.wardId }).subscribe(data => {
      this.ward = data;
    }, error => {
      alert(error.error.message)
      this._location.back();
    })
  }

  getAllDistrict() {
    this.http.getRequest("/Vietnamese-Administrative-Unit/districts/get-all").subscribe(data => {
      this.districts = data;
    }, error => {
      alert(error.error.message);
    })
  }
}
