import { DistrictPayload } from './../payload/DistrictPayload';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpService } from './../service/httpService/http.service';
import { ExcelService } from './../service/report/excel/excel.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-districts',
  templateUrl: './districts.component.html',
  styleUrls: ['./districts.component.scss']
})
export class DistrictsComponent implements OnInit {
  result!: number;
  fileName = 'ReportDistricts';
  districts!: DistrictPayload[];
  columns!: any[];

  key!: string;
  constructor(private http: HttpService, private excelService: ExcelService, private router: Router, private activatedRouter: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.columns = ["ID", "NAME", "TYPE", "PROVINCE ID"];
    this.activatedRouter.queryParams.subscribe(data => {
      if (data.key == null || data.key == "") {
        this.getAll();
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
    this.http.getRequest("/Vietnamese-Administrative-Unit/districts/get-all").subscribe(data => {
      this.districts = data;
      this.result = data.length;
    }, error => {
      alert(error.error.message);
    })
  }

  search() {
    if (this.key == null || this.key == "") {
      this.router.navigate(["/districts"])
      return
    }
    this.router.navigate(["/districts"], { queryParams: { 'key': this.key } })
  }

  getSearch(key: string) {
    this.http.getRequest("/Vietnamese-Administrative-Unit/districts/search?key=" + key).subscribe(data => {
      this.districts = data; 
      this.result = data.length;
    })
  }

  edit(id: number) {
    this.router.navigate(["/districts/edit"], { queryParams: { 'districtId': id } })
  }

  delete(item: DistrictPayload) {
    if (confirm("Are you sure you want to delete this district?")) {
      this.http.deleteRequest("/Vietnamese-Administrative-Unit/districts/delete?id=" + item.id, "").subscribe(data => {
        this.districts = data;
        this.result = data.length;
      }, error => {
        alert("Can't delete: " + error.error.message);
      });
    }
  }

  reportExcel(): void {
    this.excelService.reportAsExcelFile("Districts Report", '', this.columns, this.districts, null, this.fileName, this.fileName)
  }

}
