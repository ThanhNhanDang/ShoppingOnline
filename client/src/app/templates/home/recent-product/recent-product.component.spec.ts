import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecentProductComponent } from './recent-product.component';

describe('RecentProductComponent', () => {
  let component: RecentProductComponent;
  let fixture: ComponentFixture<RecentProductComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecentProductComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RecentProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
