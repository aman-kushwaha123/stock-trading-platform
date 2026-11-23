import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateCashBalanceComponent } from './update-cash-balance.component';

describe('UpdateCashBalanceComponent', () => {
  let component: UpdateCashBalanceComponent;
  let fixture: ComponentFixture<UpdateCashBalanceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpdateCashBalanceComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UpdateCashBalanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
