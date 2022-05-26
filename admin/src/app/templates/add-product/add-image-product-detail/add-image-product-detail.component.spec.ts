import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddImageProductDetailComponent } from './add-image-product-detail.component';

describe('AddImageProductDetailComponent', () => {
  let component: AddImageProductDetailComponent;
  let fixture: ComponentFixture<AddImageProductDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddImageProductDetailComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddImageProductDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
