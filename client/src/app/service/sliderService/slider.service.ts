import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SliderService {
  brandUrlImgs=[
    "assets/img/brand-1.png",
    "assets/img/brand-2.png",
    "assets/img/brand-3.png",
    "assets/img/brand-4.png",
    "assets/img/brand-5.png",
    "assets/img/brand-6.png"
  ]
  reviewSlider={
    autoplay: true,
    dots: false,
    infinite: true,
    slidesToShow: 2,
    slidesToScroll: 1,
    responsive: [
        {
            breakpoint: 768,
            settings: {
                slidesToShow: 1,
            }
        }
    ]
  }
  slideConfigDetail={
    speed:2000,
    autoplaySpeed: 1000,
    autoplay: true,
    infinite: true,
    dots: false,
    fade: true,
    accessibility:true,
    slidesToShow: 1,
    slidesToScroll: 1,
    asNavFor:".slide-2",
  }
  slideConfigNavDetail={
    slidesToShow: 3,
    slidesToScroll: 1,
    dots: false,
    centerMode: true,
    accessibility:true,
    focusOnSelect: true,
    asNavFor:".slide-1",
  }
  headerSlider={
        autoplay: true,
        dots: true,
        infinite: true,
        slidesToShow: 1,
        slidesToScroll: 1
  }

  brandSlider={
    speed: 5000,
    autoplay: true,
    autoplaySpeed: 0,
    cssEase: 'linear',
    slidesToShow: 5,
    slidesToScroll: 1,
    infinite: true,
    swipeToSlide: true,
    centerMode: true,
    focusOnSelect: false,
    arrows: false,
    dots: false,
    responsive: [
        {
            breakpoint: 992,
            settings: {
                slidesToShow: 4,
            }
        },
        {
            breakpoint: 768,
            settings: {
                slidesToShow: 3,
            }
        },
        {
            breakpoint: 576,
            settings: {
                slidesToShow: 2,
            }
        },
        {
            breakpoint: 300,
            settings: {
                slidesToShow: 1,
            }
        }
    ]
  }
  
  recentProduct={
    autoplay: true,
        infinite: true,
        dots: false,
        slidesToShow: 4,
        slidesToScroll: 1,
        responsive: [
            {
                breakpoint: 1200,
                settings: {
                    slidesToShow: 4,
                }
            },
            {
                breakpoint: 992,
                settings: {
                    slidesToShow: 3,
                }
            },
            {
                breakpoint: 768,
                settings: {
                    slidesToShow: 2,
                }
            },
            {
                breakpoint: 576,
                settings: {
                    slidesToShow: 1,
                }
            },
        ]
  }
  sidebarSlider={
    autoplay: true,
    dots: false,
    infinite: true,
    slidesToShow: 1,
    slidesToScroll: 1
  }

  relateProduct={
    autoplay: true,
    infinite: true,
    dots: false,
    slidesToShow: 3,
    slidesToScroll: 1,
    responsive: [
          {
            breakpoint: 992,
            settings: {
                slidesToShow: 3,
              }
          },
          {
            breakpoint: 768,
            settings: {
            slidesToShow: 2,
              }
        },
        {
            breakpoint: 576,
            settings: {
            slidesToShow: 1,
        }
            },
        ]
  }
  constructor() { }

  getSlideConfigDetail(){
    return this.slideConfigDetail;
  }
  getSlideConfigNavDetail(){
    return this.slideConfigNavDetail;
  }
  getHeaderSlider(){
    return this.headerSlider;
  }
  getBrandSlider(){
    return this.brandSlider;
  }

  getBrandUrlImgs(){
    return this.brandUrlImgs;
  }

  getReviewSlider(){
    return this.reviewSlider;
  }
  getRecentProduct(){
    return this.recentProduct;
  }
  getSidebarSlider(){
    return this.sidebarSlider;
  }

  getRelateProduct(){
    return this.relateProduct;
  }
}
