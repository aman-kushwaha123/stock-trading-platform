import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PortfolioSnapshotPageComponent } from './portfolio-snapshot-page.component';

describe('PortfolioSnapshotPageComponent', () => {
  let component: PortfolioSnapshotPageComponent;
  let fixture: ComponentFixture<PortfolioSnapshotPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PortfolioSnapshotPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PortfolioSnapshotPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
