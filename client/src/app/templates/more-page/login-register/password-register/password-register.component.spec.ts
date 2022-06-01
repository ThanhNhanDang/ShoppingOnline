import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PasswordRegisterComponent } from './password-register.component';

describe('PasswordRegisterComponent', () => {
  let component: PasswordRegisterComponent;
  let fixture: ComponentFixture<PasswordRegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PasswordRegisterComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PasswordRegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
