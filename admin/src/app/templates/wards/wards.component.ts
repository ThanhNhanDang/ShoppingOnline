import { DistrictPayload } from './../payload/DistrictPayload';
import { Router, ActivatedRoute } from '@angular/router';
import { ExcelService } from './../service/report/excel/excel.service';
import { HttpService } from './../service/httpService/http.service';
import { WardPayload } from './../payload/WardPayload';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-wards',
  templateUrl: './wards.component.html',
  styleUrls: ['./wards.component.scss']
})
export class WardsComponent implements OnInit {
  key2 = 1;
  result!: number;
  fileName = 'ReportWards';
  wards!: WardPayload[];
  columns!: any[];
  districts: DistrictPayload[] = [];

  key!: string;
  constructor(private http: HttpService, private excelService: ExcelService, private router: Router, private activatedRouter: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.getAllDistrict();
    this.columns = ["ID", "NAME", "TYPE", "DISTRICT ID"];
    this.activatedRouter.queryParams.subscribe(data => {
      if (data.key == null || data.key == "") {
        this.key2 = 1;
        this.getWardsByDistrict();
        return
      }
      else {
        this.key = data.key
        this.getSearch(data.key);
        return;
       
      }
    })
  }
  getAll() {
    this.http.getRequest("/Vietnamese-Administrative-Unit/wards/get-all").subscribe(data => {
      this.wards = data;
      this.result = data.length;
    }, error => {
      alert(error.error.message);
    })
  }

  search() {
    if (this.key == null || this.key == "") {
      this.router.navigate(["/wards"])
      return
    }
    this.router.navigate(["/wards"], { queryParams: { 'key': this.key } })
  }

  getSearch(key: string) {
    this.http.getRequest("/Vietnamese-Administrative-Unit/wards/search?key=" + key).subscribe(data => {
      this.wards = data; 
      this.result = data.length;
    })
  }

  edit(id: number) {
    this.router.navigate(["/wards/edit"], { queryParams: { 'wardId': id } })
  }

  delete(item: WardPayload) {
    if (confirm("Are you sure you want to delete this ward?")) {
      this.http.deleteRequest("/Vietnamese-Administrative-Unit/wards/delete?id=" + item.id, "").subscribe(data => {
        this.getWardsByDistrict();
      }, error => {
        alert("Can't delete: " + error.error.message);
      });
    }
  }

  reportExcel(): void {
    this.excelService.reportAsExcelFile("Wards Report", '', this.columns, this.wards, null, this.fileName, this.fileName)
  }

  public getWardsByDistrict() {
    this.http.postRequest("/Vietnamese-Administrative-Unit/wards/get-all-by-district-id", { districtId: this.key2 }).subscribe(data => {
      this.wards = data;
      this.result = data.length;
    }, error => {
      alert(error.error.message)
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
