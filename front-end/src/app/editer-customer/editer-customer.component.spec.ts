import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditerCustomerComponent } from './editer-customer.component';

describe('EditerCustomerComponent', () => {
  let component: EditerCustomerComponent;
  let fixture: ComponentFixture<EditerCustomerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditerCustomerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EditerCustomerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
