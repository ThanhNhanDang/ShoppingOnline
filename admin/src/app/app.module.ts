import { UpdateDefaultImageComponent } from './templates/update-default-image/update-default-image.component';
import { BasicAuthInterceptor } from './templates/service/BasicAuthInterceptor';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavComponent } from './component/top/nav/nav.component';
import { HomeComponent } from './templates/home/home.component';
import { FooterComponent } from './component/footer/footer.component';
import { ContentRowComponent } from './templates/home/content-row/content-row.component';
import { LineChartComponent } from './templates/home/content-row/line-chart/line-chart.component';
import { NgxEchartsModule } from 'ngx-echarts';
import { PieChartComponent } from './templates/home/content-row/pie-chart/pie-chart.component';
import { BarChartComponent } from './templates/home/content-row/bar-chart/bar-chart.component';
import { ProductComponent } from './templates/product/product.component';
import { AddProductComponent } from './templates/add-product/add-product.component';
import { AddCategoryComponent } from './templates/add-category/add-category.component';
import { AccountComponent } from './templates/account/account.component';
import { LoginComponent } from './templates/login/login.component';
import { EditProductComponent } from './templates/edit-product/edit-product.component';
import { DatepickerDirective } from './templates/add-product/datepicker.directive';
import { CategoryComponent } from './templates/category/category.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { EditCategoryComponent } from './templates/edit-category/edit-category.component';
import { SpinnerComponent } from './templates/spinner/spinner.component';
import { UserListComponent } from './templates/user-list/user-list.component';
import { AddUserComponent } from './templates/user-list/add-user/add-user.component';
import { AddImageProductDetailComponent } from './templates/add-product/add-image-product-detail/add-image-product-detail.component';
import { ProvincesComponent } from './templates/provinces/provinces.component';
import { DistrictsComponent } from './templates/districts/districts.component';
import { WardsComponent } from './templates/wards/wards.component';
import { UpdateComponent } from './templates/provinces/update/update.component';
import { AddComponent } from './templates/provinces/add/add.component';
import { AddDistrictComponent } from './templates/districts/add-district/add-district.component';
import { UpdateDistrictComponent } from './templates/districts/update-district/update-district.component';
import { UpdateWardComponent } from './templates/wards/update-ward/update-ward.component';
import { AddWardComponent } from './templates/wards/add-ward/add-ward.component';

@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    HomeComponent,
    FooterComponent,
    ContentRowComponent,
    LineChartComponent,
    PieChartComponent,
    BarChartComponent,
    ProductComponent,
    AddProductComponent,
    AddCategoryComponent,
    AccountComponent,
    LoginComponent,
    EditProductComponent,
    DatepickerDirective,
    CategoryComponent,
    EditCategoryComponent,
    SpinnerComponent,
    UserListComponent,
    AddUserComponent,
    AddImageProductDetailComponent,
    ProvincesComponent,
    DistrictsComponent,
    WardsComponent,
    UpdateComponent,
    AddComponent,
    AddDistrictComponent,
    UpdateDistrictComponent,
    UpdateWardComponent,
    AddWardComponent,
    UpdateDefaultImageComponent,
    
  
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgxEchartsModule.forRoot({
      echarts: () => import('echarts')
    }),
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: BasicAuthInterceptor, multi: true }, DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
