import { DistrictPayload } from './../../payload/DistrictPayload';
import { ProvincePayload } from './../../payload/ProvincePayload';
import { ExcelService } from './../../service/report/excel/excel.service';
import { HttpService } from './../../service/httpService/http.service';
import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-add-district',
  templateUrl: './add-district.component.html',
  styleUrls: ['./add-district.component.scss']
})
export class AddDistrictComponent implements OnInit {
  district!: DistrictPayload;
  districts: DistrictPayload[] = [];

  fileName!: String;
  provinces: ProvincePayload[] = [];
  constructor(private http: HttpService, private excelService: ExcelService, private _location: Location) { }

  ngOnInit(): void {
    this.district = new DistrictPayload();
    this.getAllProvince();
  }

  getAllProvince() {
    this.http.getRequest("/Vietnamese-Administrative-Unit/provinces/get-all").subscribe(data => {
      this.provinces = data;
    }, error => {
      alert(error.error.message);
    })
  }
  public add() {
    if (this.district.name === "" || this.district.name === undefined || this.district.name === null || this.district.type === "" || this.district.type === undefined || this.district.type === null)
      return
    this.http.postRequest("/Vietnamese-Administrative-Unit/districts/save", this.district).subscribe(() => {
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
    this.districts = this.excelService.readExcelDistricts(event)
  }

  public addAll() {
    if (this.districts.length === 0)
      return;

    this.http.postRequest("/Vietnamese-Administrative-Unit/districts/save-all", this.districts).subscribe(() => {
      alert("Successful")
      this._location.back();
    }, error => {
      alert(error.error.message)
    })
  }

}
