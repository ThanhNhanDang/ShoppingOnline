import { SearchAndSortPayload } from './../../payload/SearchAndSortPayload';
import { environment } from './../../../environments/environment';
import { ProductService } from './../../service/product/product.service';
import { ProductPayload } from './../../payload/ProductPayload';
import { Subscription } from 'rxjs';
import { HttpServiceService } from './../../service/httpService/http-service.service';
import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';

interface sortItem {
  id: number;
  name: string;
  value: string;
  oldOrNew: boolean;
}

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})

export class ProductsComponent implements OnInit {
  searAndSort!: SearchAndSortPayload;
  products!: ProductPayload[];
  item = "Hello";
  lastPage!: number;
  totalElements = 0;
  active = false;

  priceRange: any[] = []

  cateName = "";
  //sort
  sortKey = "Newest";
  sortKeyPrice = "";
  sortId = 1;
  sortPriceRangeId = 0;

  sortKeys: sortItem[] = [
    { id: 1, name: "Newest", value: "id", oldOrNew: true },
    { id: 2, name: "Oldest", value: "id", oldOrNew: false },
    { id: 3, name: "Popular", value: "totalReview5Star", oldOrNew: true },
    { id: 4, name: "Most sale", value: "unitSold", oldOrNew: true }
  ]
  checkSort = false;
  //sort
  //page
  previousNext !: number;
  selectedItem !: number;
  //end page

  changeProductByCategory!: Subscription;
  baseUrl = environment.urlServe;
  j = 0;
  priceRangeFn() {
    let end = 0;
    for (let index = 0; index < 10; index++) {
      const element = { id: index + 1, start: end + 1, end: end += 50 };
      this.priceRange.push(element);
    }
    const element = { id: this.priceRange.length + 1, start: end, end: null };
    this.priceRange.push(element);
  }

  constructor(private router: Router, private activatedRouter: ActivatedRoute, private _location: Location, public http: HttpServiceService, private productService: ProductService) {
    this.priceRangeFn();
  }
  ngOnInit(): void {
    this.previousNext = this.selectedItem = 1
    this.activatedRouter.queryParams.subscribe(data => {
      this.searAndSort = new SearchAndSortPayload();
      if (data.key != null) {
        this.searAndSort.key = data.key;
      }
      if (data.value != null) {
        this.searAndSort.sortKey = data.value;
        if (data.sortId != null) {
          this.sortKey = this.sortKeys[data.sortId - 1].name
          this.sortId = this.sortKeys[data.sortId - 1].id;
        }
        if (data.sortPriceId != null) {
          let sortPrice = this.priceRange[data.sortPriceId - 1];
          this.sortPriceRangeId = sortPrice.id;
          sortPrice.end == null ? this.sortKeyPrice = "more than " + sortPrice.start : this.sortKeyPrice = sortPrice.start + "-" + sortPrice.end;

        }
      }
      else {
        this.searAndSort.sortKey = "id";
        this.sortKey = "Newest";
        this.sortKeyPrice = "";
        this.sortId = 1;
        this.sortPriceRangeId = 0;
      }

      if (data.icate != null) {
        this.searAndSort.cateId = data.icate;
      }
      if (data.page != null && data.page > 0) {
        this.previousNext = this.selectedItem = data.page;
      }
      if (data.newest != null) {
        this.searAndSort.oldOrNew = data.newest;
      }
      this.searAndSort.page = this.previousNext - 1
      this.getPage();
      if (data.caterory != null) {
        this.cateName = data.caterory;
        this.active = true;
      }
      else
        this.active = false;
    })
    this.http.setTitle("E Store-Products")

  }

  getPage() {
    this.http.postRequest("/product/search", this.searAndSort).subscribe(data => {
      this.totalElements = data.totalElements;
      this.products = data.content;
      this.lastPage = data.totalPages;
    }, error => {
      alert("Server connection error")
    })
    window.scroll(0, 0);
  }

  clickPage(page: number) {
    this.previousNext = page;
    this.page()
  }
  page() {
    this.selectedItem = this.previousNext;
    this.searAndSort.page = this.previousNext - 1
    this.router.navigate(['/products'], { queryParamsHandling: "merge", queryParams: { 'page': this.previousNext } });
    this.getPage();
  }
  nextPage() {
    this.previousNext++;
    if (this.previousNext > this.lastPage) {
      this.previousNext = this.lastPage;
      return;
    }

    this.page()
  }

  previousPage() {
    this.previousNext--
    if (this.previousNext < 1) {
      this.previousNext = 1;
      return;
    }
    this.page()
  }


  backClicked() {
    this._location.back();
  }

  sort(item: sortItem) {
    this.sortId = item.id;
    this.sortKey = item.name;
    this.reset()
    this.router.navigate(['/products'], { queryParamsHandling: "merge", queryParams: { 'page': this.previousNext, 'value': item.value, 'newest': item.oldOrNew, sortId: this.sortId } });
  }

  sortPrice(item: any) {
    this.sortPriceRangeId = item.id;
    item.end == null ? this.sortKeyPrice = "more than " + item.start : this.sortKeyPrice = item.start + "-" + item.end;
    this.reset()
    this.router.navigate(['/products'], { queryParamsHandling: "merge", queryParams: { 'page': this.previousNext, 'priceStart': item.start, 'priceEnd': item.end == null ? "more than" : item.end, sortPriceId: this.sortPriceRangeId } });
  }

  search() {
    this.reset()
    if (this.searAndSort.key.length < 3) {
      alert("Keyword too short.")
      return;
    }

    this.router.navigate(['/products'], { queryParamsHandling: "merge", queryParams: { 'page': this.previousNext, 'key': this.searAndSort.key } });
  }

  reset() {
    this.previousNext = this.selectedItem = 1;
  }
  sortPriceNone() {
    this.router.navigate(['/products'], { queryParams: { 'page': this.previousNext } });
  }
}
