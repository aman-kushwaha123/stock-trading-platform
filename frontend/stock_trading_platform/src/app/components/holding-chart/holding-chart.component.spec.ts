import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HoldingChartComponent } from './holding-chart.component';

describe('HoldingChartComponent', () => {
  let component: HoldingChartComponent;
  let fixture: ComponentFixture<HoldingChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HoldingChartComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HoldingChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
