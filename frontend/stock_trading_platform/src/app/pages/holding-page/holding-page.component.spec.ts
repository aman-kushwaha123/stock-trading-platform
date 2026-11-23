import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HoldingPageComponent } from './holding-page.component';

describe('HoldingPageComponent', () => {
  let component: HoldingPageComponent;
  let fixture: ComponentFixture<HoldingPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HoldingPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HoldingPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
