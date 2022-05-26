import { ProvincePayload } from './../payload/ProvincePayload';
import { ExcelService } from './../service/report/excel/excel.service';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpService } from './../service/httpService/http.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-provinces',
  templateUrl: './provinces.component.html',
  styleUrls: ['./provinces.component.scss']
})
export class ProvincesComponent implements OnInit {
  result!: number;
  fileName = 'ReportProvinces';
  provinces!: ProvincePayload[];
  columns!: any[];

  key!: string;
  constructor(private http: HttpService, private excelService: ExcelService, private router: Router, private activatedRouter: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.columns = ["ID", "NAME", "TYPE"];
    this.activatedRouter.queryParams.subscribe(data => {
      if (data.key == null || data.key == "") {
        this.getAllProvince();
        return
      }
      else {
        this.key = data.key
        this.getSearch(data.key);
        return;
      }
    })


  }
  getAllProvince() {
    this.http.getRequest("/Vietnamese-Administrative-Unit/provinces/get-all").subscribe(data => {
      this.provinces = data;
      this.result = data.length;
    }, error => {
      alert(error.error.message);
    })
  }

  search() {
    if (this.key == null || this.key == "") {
      this.router.navigate(["/provinces"])
      return
    }
    this.router.navigate(["/provinces"], { queryParams: { 'key': this.key } })
  }

  getSearch(key: string) {
    this.http.getRequest("/Vietnamese-Administrative-Unit/provinces/search?key=" + key).subscribe(data => {
      this.provinces = data;
      this.result = data.length;
    })
  }

  edit(id: number) {
    this.router.navigate(["/provinces/edit"], { queryParams: { 'provinceId': id } })
  }

  delete(item: ProvincePayload) {
    if (confirm("Are you sure you want to delete this province?")) {
      this.http.deleteRequest("/Vietnamese-Administrative-Unit/provinces/delete?id=" + item.id, "").subscribe(data => {
        this.provinces = data;
        this.result = data.length;
      }, error => {
        alert("Can't delete: " + error.error.message);
      });
    }
  }

  reportExcel(): void {
    this.excelService.reportAsExcelFile("Provinces Report", '', this.columns, this.provinces, null, this.fileName, this.fileName)
  }
}
