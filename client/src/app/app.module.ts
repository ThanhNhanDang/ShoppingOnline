import { BasicAuthInterceptor } from './service/BasicAuthInterceptor';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser/';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavComponent } from './components/top/nav/nav.component';
import { FooterComponent } from './components/main-footer/footer/footer.component';
import { BottomBarComponent } from './components/bottom/bottom-bar/bottom-bar.component';
import { TopBarComponent } from './components/top/top-bar/top-bar.component';
import { MainBannerComponent } from './templates/home/main-banner/main-banner.component';
import { NavbarComponent } from './components/top/navbar/navbar.component';
import { BrandComponent } from './components/body/brand/brand.component';
import { FeatureComponent } from './templates/home/feature/feature.component';
import { CategoryComponent } from './templates/home/category/category.component';
import { CallToActionComponent } from './templates/home/call-to-action/call-to-action.component';
import { FeatureProductComponent } from './templates/home/feature-product/feature-product.component';
import { NewsletterComponent } from './templates/home/newsletter/newsletter.component';
import { RecentProductComponent } from './templates/home/recent-product/recent-product.component';
import { ReviewComponent } from './templates/home/review/review.component';
import { FooterBottomComponent } from './components/main-footer/footer-bottom/footer-bottom.component';
import { BestSellingComponent } from './components/body/best-selling/best-selling.component';
import { NewArrivalsComponent } from './templates/new-arrivals/new-arrivals.component';
import { ProductDetailComponent } from './templates/product-detail/product-detail.component';
import { CartComponent } from './templates/cart/cart.component';
import { WishListComponent } from './templates/more-page/wish-list/wish-list.component';
import { BannerComponent } from './components/body/banner/banner.component';
import { MainTopComponent } from './components/top/main-top/main-top.component';
import { MainBodyComponent } from './components/body/main-body/main-body.component';
import { MainFooterComponent } from './components/main-footer/main-footer.component';
import { HomeComponent } from './templates/home/home.component';
import { ProductsComponent } from './templates/products/products.component';
import { MyAccountComponent } from './templates/my-account/my-account.component';
import { SlidebarComponent } from './templates/products/slidebar/slidebar.component';
import { TagComponent } from './templates/products/slidebar/tag/tag.component';
import { BrandsComponent } from './templates/products/slidebar/brands/brands.component';
import { SliderComponent } from './templates/products/slidebar/slider/slider.component';
import { CatComponent } from './templates/products/slidebar/cat/cat.component';
import { ContactUsComponent } from './templates/more-page/contact-us/contact-us.component';
import { LoginComponent } from './templates/more-page/login-register/login/login.component';
import { RegisterComponent } from './templates/more-page/login-register/register/register.component';
import { BodyComponent } from './components/body/body.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SlickCarouselModule } from 'ngx-slick-carousel';
import { UserComponent } from './templates/admin/user/user.component';
import { MyOrderComponent } from './templates/my-order/my-order.component';
import { CheckoutComponent } from './templates/checkout/checkout.component';
import { MyReviewComponent } from './templates/my-review/my-review.component';
import { E404Component } from './templates/errors/e404/e404.component';
import { E403Component } from './templates/errors/e403/e403.component';
import { SpinnerComponent } from './templates/spinner/spinner.component';
import { SearchComponent } from './components/bottom/bottom-bar/search/search.component';
import { SuccessComponent } from './templates/more-page/login-register/register/success/success.component';
import { ActiveComponent } from './templates/more-page/login-register/register/success/active/active.component';

import { SocialLoginModule, SocialAuthServiceConfig } from 'angularx-social-login';
import {
  GoogleLoginProvider,
  FacebookLoginProvider
} from 'angularx-social-login';
import { ForgotPasswordComponent } from './templates/more-page/forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './templates/more-page/reset-password/reset-password.component';
import { ProductComponent } from './templates/product/product.component';

@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    FooterComponent,
    BottomBarComponent,
    TopBarComponent,
    MainBannerComponent,
    NavbarComponent,
    BrandComponent,
    FeatureComponent,
    CategoryComponent,
    CallToActionComponent,
    FeatureProductComponent,
    NewsletterComponent,
    RecentProductComponent,
    ReviewComponent,
    FooterBottomComponent,
    BestSellingComponent,
    NewArrivalsComponent,
    ProductDetailComponent,
    CartComponent,
    WishListComponent,
    BannerComponent,
    MainTopComponent,
    MainBodyComponent,
    MainFooterComponent,
    HomeComponent,
    ProductsComponent,
    MyAccountComponent,
    SlidebarComponent,
    TagComponent,
    BrandsComponent,
    SliderComponent,
    CatComponent,
    ContactUsComponent,
    LoginComponent,
    RegisterComponent,
    BodyComponent,
    UserComponent,
    MyOrderComponent,
    ProductComponent,
    CheckoutComponent,
    MyReviewComponent,
    E404Component,
    E403Component,
    SpinnerComponent,
    SearchComponent,
    SuccessComponent,
    ActiveComponent,
    ForgotPasswordComponent,
    ResetPasswordComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
    SlickCarouselModule,
    SocialLoginModule
  ],
  providers: [
    {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider(
              '295700504694-dp7cam8r4cdjq5ia1mv8qc31448gs0vm.apps.googleusercontent.com'
            )
          },
          {
            id: FacebookLoginProvider.PROVIDER_ID,
            provider: new FacebookLoginProvider('554519459001774')
          }
        ]
      } as SocialAuthServiceConfig,
    },
    {
      provide: HTTP_INTERCEPTORS, useClass: BasicAuthInterceptor, multi: true
    },

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
