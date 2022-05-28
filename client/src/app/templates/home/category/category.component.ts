import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { CategoryPayload } from './../../../payload/CategoryPayload';
import { Component, OnInit } from '@angular/core';
import { HttpServiceService } from 'src/app/service/httpService/http-service.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {

  categories: CategoryPayload[] = [];

  constructor(private http: HttpServiceService, private router: Router) { }


  ngOnInit(): void {
    this.http.getRequest("/category/all", "").subscribe(data => {
      this.categories = data
    })
  }

  class(category: any) {
    this.router.navigate(['/products'], { queryParams: { 'caterory': category.name, 'icate': category.id, 'page': 1 } });
  }

}
