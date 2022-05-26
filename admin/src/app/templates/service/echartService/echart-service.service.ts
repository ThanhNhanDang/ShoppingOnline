import { environment } from './../../../../environments/environment';
import { LineChartPayload } from './../../payload/lineChart.payload';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EchartServiceService {
  baseUrl = environment.baseUrl;
  constructor(private httpClient:HttpClient) { }

  getLineChart():Observable<LineChartPayload[]>{
      return this.httpClient.get<LineChartPayload[]>(this.baseUrl + "/lineChart/get");
  }
}
