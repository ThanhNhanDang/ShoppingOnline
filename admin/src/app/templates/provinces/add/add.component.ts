import { ExcelService } from './../../service/report/excel/excel.service';
import { ProvincePayload } from './../../payload/ProvincePayload';
import { HttpService } from './../../service/httpService/http.service';
import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss']
})
export class AddComponent implements OnInit {
  province!: ProvincePayload;
  fileName!: string;
  provinces: ProvincePayload[] = [];

  constructor(private http: HttpService, private excelService: ExcelService, private _location: Location) { }

  ngOnInit(): void {
    this.province = new ProvincePayload();
  }

  public add() {
    if (this.province.name === "" || this.province.name === undefined || this.province.name === null || this.province.type === "" || this.province.type === undefined || this.province.type === null)
      return
    this.http.postRequest("/Vietnamese-Administrative-Unit/provinces/save", this.province).subscribe(() => {
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
    this.provinces = this.excelService.readExcelProvinces(event)
  }

  public addAll() {
    if (this.provinces.length === 0)
      return;
    
    this.http.postRequest("/Vietnamese-Administrative-Unit/provinces/save-all", this.provinces).subscribe(() => {
      alert("Successful")
      this._location.back();
    }, error => {
      alert(error.error.message)
    })
  }

}
