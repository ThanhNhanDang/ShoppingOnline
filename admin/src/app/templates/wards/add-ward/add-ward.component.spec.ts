import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddWardComponent } from './add-ward.component';

describe('AddWardComponent', () => {
  let component: AddWardComponent;
  let fixture: ComponentFixture<AddWardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddWardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddWardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
