import { ComponentFixture, TestBed } from '@angular/core/testing';
import { UpdateDefaultImageComponent } from './update-default-image.component';

describe('UpdateDefaultImageComponent', () => {
  let component: UpdateDefaultImageComponent;
  let fixture: ComponentFixture<UpdateDefaultImageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateDefaultImageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateDefaultImageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
