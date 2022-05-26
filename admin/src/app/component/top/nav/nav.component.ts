import { OrderPayload } from './../../../templates/payload/OrderPayload';
import { ExcelService } from './../../../templates/service/report/excel/excel.service';

import { HttpService } from './../../../templates/service/httpService/http.service';
import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss']
})
export class NavComponent implements OnInit {
  checkLogout = true;

  columnsProvince!: any[];
  columnsDistrict!: any[];
  columnsWard!: any[];
  columnsOrder!: any[];

  fileNameProvince = "ReportProvince";
  fileNameDistrict = "ReportDistrict";
  fileNameWard = "ReportWard";
  fileNameOrder = "ReportOrder";

  userId = this.http.getLoginDataByKey("id");
  subjectWhenLogin!: Subscription;
  constructor(private http: HttpService, private excelService: ExcelService) { }

  ngOnInit(): void {
    this.columnsProvince = ["ID", "NAME", "TYPE"];
    this.columnsDistrict = ["ID", "NAME", "TYPE", "PROVINCE ID"];
    this.columnsWard = ["ID", "NAME", "TYPE", "DISTRICT ID"];
    this.columnsOrder = ["ID", "PRODUCT ID", "QUANTITY", "TOTAL AMOUNT", "START DATE", "CREATOR ID", "ORDER NO.", "PAYMENT TYPE", "LOCATION", "ORDER CREATOR", "PRODUCT NAME", "PRICE PER ITEM"];
    this.checkLogout = this.http.isLogin()
    this.subjectWhenLogin = this.http.getSubjectLogin().subscribe(() => {
      this.checkLogout = this.http.isLogin()
    })
  }
  logout() {
    this.checkLogout = false;
    this.http.logout();
  }
  reportExcelProvince(): void {
    this.http.getRequest("/Vietnamese-Administrative-Unit/provinces/get-all").subscribe(data => {
      this.excelService.reportAsExcelFile("Report Provinces", '', this.columnsProvince, data, null, this.fileNameProvince, this.fileNameProvince)
    }, error => {
      alert(error.error.message);
    })
  }
  reportExcelDistrict(): void {
    this.http.getRequest("/Vietnamese-Administrative-Unit/districts/get-all").subscribe(data => {
      this.excelService.reportAsExcelFile("Report Districts", '', this.columnsDistrict, data, null, this.fileNameDistrict, this.fileNameDistrict)
    }, error => {
      alert(error.error.message);
    })
  }
  reportExcelWard(): void {
    this.http.getRequest("/Vietnamese-Administrative-Unit/wards/get-all").subscribe(data => {
      this.excelService.reportAsExcelFile("Report Wards", '', this.columnsWard, data, null, this.fileNameWard, this.fileNameWard)

    }, error => {
      alert(error.error.message);
    })
  }
  reportExcelOrder(): void {
    this.http.getRequest("/order/get-all").subscribe(data => {
      this.excelService.reportAsExcelFile("Report Orders", '', this.columnsOrder, data, null, this.fileNameOrder, this.fileNameOrder)
    }, error => {
      alert(error.error.message);
    })
  }
}
