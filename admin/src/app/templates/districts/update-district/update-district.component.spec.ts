import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateDistrictComponent } from './update-district.component';

describe('UpdateDistrictComponent', () => {
  let component: UpdateDistrictComponent;
  let fixture: ComponentFixture<UpdateDistrictComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateDistrictComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateDistrictComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
