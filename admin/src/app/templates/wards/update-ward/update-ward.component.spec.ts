import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateWardComponent } from './update-ward.component';

describe('UpdateWardComponent', () => {
  let component: UpdateWardComponent;
  let fixture: ComponentFixture<UpdateWardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateWardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateWardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
