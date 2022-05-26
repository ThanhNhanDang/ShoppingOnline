import { ExcelService } from './../../service/report/excel/excel.service';
import { HttpService } from './../../service/httpService/http.service';
import { DistrictPayload } from './../../payload/DistrictPayload';
import { WardPayload } from './../../payload/WardPayload';
import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-add-ward',
  templateUrl: './add-ward.component.html',
  styleUrls: ['./add-ward.component.scss']
})
export class AddWardComponent implements OnInit {
  ward!: WardPayload;
  wards: WardPayload[] = [];

  fileName!: String;
  districts: DistrictPayload[] = [];
  constructor(private http: HttpService, private excelService: ExcelService, private _location: Location) { }

  ngOnInit(): void {
    this.ward = new WardPayload();
    this.getAllDistrict();
  }

  getAllDistrict() {
    this.http.getRequest("/Vietnamese-Administrative-Unit/districts/get-all").subscribe(data => {
      this.districts = data;
    }, error => {
      alert(error.error.message);
    })
  }
  public add() {
    if (this.ward.name === "" || this.ward.name === undefined || this.ward.name === null || this.ward.type === "" || this.ward.type === undefined || this.ward.type === null)
      return
    this.http.postRequest("/Vietnamese-Administrative-Unit/wards/save", this.ward).subscribe(() => {
      alert("Successful")
      this._location.back();
    }, error => {
      alert(error.error.message)
    })
  }

  public readExcel(event: any, files: any) {
    if (files.length === 0)
      return;
    this.fileName = files.item(0).name;
    this.wards = this.excelService.readExcelWards(event)
  }

  public addAll() {
    if (this.wards.length === 0)
      return;

    this.http.postRequest("/Vietnamese-Administrative-Unit/wards/save-all", this.wards).subscribe(() => {
      alert("Successful")
      this._location.back();
    }, error => {
      alert(error.error.message)
    })
  }

}
