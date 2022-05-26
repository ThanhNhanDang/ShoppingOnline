
import { ActiveComponent } from './templates/more-page/login-register/register/success/active/active.component';
import { SuccessComponent } from './templates/more-page/login-register/register/success/success.component';
import { GuardCanActivateGuard } from './guard-can-activate.guard';

import { E403Component } from './templates/errors/e403/e403.component';
import { E404Component } from './templates/errors/e404/e404.component';
import { MyReviewComponent } from './templates/my-review/my-review.component';
import { CheckoutComponent } from './templates/checkout/checkout.component';
import { MyOrderComponent } from './templates/my-order/my-order.component';
import { RegisterComponent } from './templates/more-page/login-register/register/register.component';
import { WishListComponent } from './templates/more-page/wish-list/wish-list.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CartComponent } from './templates/cart/cart.component';
import { ProductDetailComponent } from './templates/product-detail/product-detail.component';
import { ProductsComponent } from './templates/products/products.component';
import { HomeComponent } from './templates/home/home.component';
import { MyAccountComponent } from './templates/my-account/my-account.component';
import { ContactUsComponent } from './templates/more-page/contact-us/contact-us.component';
import { LoginComponent } from './templates/more-page/login-register/login/login.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'products', component: ProductsComponent },
  { path: 'cart', component: CartComponent, canActivate:[GuardCanActivateGuard]},
  { path: 'checkout', component: CheckoutComponent, canActivate:[GuardCanActivateGuard]},
  { path: 'my-order', component: MyOrderComponent, canActivate:[GuardCanActivateGuard]},
  { path: 'my-account', component: MyAccountComponent, canActivate:[GuardCanActivateGuard]},
  { path: 'wish-list', component: WishListComponent, canActivate:[GuardCanActivateGuard]},
  { path: 'contact-us', component:  ContactUsComponent},
  { path: 'login', component:  LoginComponent, canActivate:[GuardCanActivateGuard]},
  { path: 'register', component:  RegisterComponent, canActivate:[GuardCanActivateGuard]},
  { path: 'register/verify', component:  SuccessComponent, canActivate:[GuardCanActivateGuard]},
  { path: 'register/verify/active', component:  ActiveComponent},
  { path: 'my-reviews', component:  MyReviewComponent, canActivate:[GuardCanActivateGuard] },
  { path: '404-error', component:  E404Component},
  { path: '403-error', component:  E403Component},
  { path: 'admin', component:  CartComponent, canActivate:[GuardCanActivateGuard]},
  { path: 'product-detail', component: ProductDetailComponent},
  { path: "", redirectTo: '/home', pathMatch: 'full' },
  { path: "**", redirectTo: '/404-error', pathMatch: 'full' },
 
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
