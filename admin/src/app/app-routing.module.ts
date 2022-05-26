import { UpdateDefaultImageComponent } from './templates/update-default-image/update-default-image.component';
import { AddWardComponent } from './templates/wards/add-ward/add-ward.component';
import { UpdateWardComponent } from './templates/wards/update-ward/update-ward.component';
import { WardsComponent } from './templates/wards/wards.component';
import { AddDistrictComponent } from './templates/districts/add-district/add-district.component';
import { UpdateDistrictComponent } from './templates/districts/update-district/update-district.component';
import { DistrictsComponent } from './templates/districts/districts.component';
import { UpdateComponent } from './templates/provinces/update/update.component';
import { ProvincesComponent } from './templates/provinces/provinces.component';
import { AddImageProductDetailComponent } from './templates/add-product/add-image-product-detail/add-image-product-detail.component';
import { AddUserComponent } from './templates/user-list/add-user/add-user.component';
import { UserListComponent } from './templates/user-list/user-list.component';
import { AddCategoryComponent } from './templates/add-category/add-category.component';
import { EditCategoryComponent } from './templates/edit-category/edit-category.component';
import { CanActiveGuard } from './can-active.guard';
import { LoginComponent } from './templates/login/login.component';
import { AccountComponent } from './templates/account/account.component';
import { AddProductComponent } from './templates/add-product/add-product.component';
import { EditProductComponent } from './templates/edit-product/edit-product.component';
import { ProductComponent } from './templates/product/product.component';
import { HomeComponent } from './templates/home/home.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddComponent } from './templates/provinces/add/add.component';

const routes: Routes = [
  { path: 'dashboard', component: HomeComponent, canActivate:[CanActiveGuard] },
  { path: 'product', component: ProductComponent, canActivate:[CanActiveGuard] },
  { path: 'product/edit', component: EditProductComponent, canActivate:[CanActiveGuard] },
  { path: 'product/edit/detail-img', component: AddImageProductDetailComponent, canActivate:[CanActiveGuard] },
  { path: 'category/edit', component: EditCategoryComponent, canActivate:[CanActiveGuard] },
  { path: 'category/add', component: AddCategoryComponent, canActivate:[CanActiveGuard] },
  { path: 'product/add', component: AddProductComponent, canActivate:[CanActiveGuard] },
  { path: 'account', component: AccountComponent, canActivate:[CanActiveGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'accounts', component: UserListComponent, canActivate:[CanActiveGuard] },
  { path: 'accounts/add', component: AddUserComponent, canActivate:[CanActiveGuard] },
  { path: 'provinces', component: ProvincesComponent, canActivate:[CanActiveGuard] },
  { path: 'provinces/edit', component: UpdateComponent, canActivate:[CanActiveGuard] },
  { path: 'provinces/add', component: AddComponent, canActivate:[CanActiveGuard] },
  { path: 'districts', component: DistrictsComponent, canActivate:[CanActiveGuard] },
  { path: 'districts/edit', component: UpdateDistrictComponent, canActivate:[CanActiveGuard] },
  { path: 'districts/add', component: AddDistrictComponent, canActivate:[CanActiveGuard] },
  { path: 'wards', component: WardsComponent, canActivate:[CanActiveGuard] },
  { path: 'wards/edit', component: UpdateWardComponent, canActivate:[CanActiveGuard] },
  { path: 'wards/add', component: AddWardComponent, canActivate:[CanActiveGuard] },
  { path: 'change-default-image', component: UpdateDefaultImageComponent, canActivate:[CanActiveGuard] },
   
  
  { path: "", redirectTo: '/dashboard', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
