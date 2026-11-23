import { TestBed } from '@angular/core/testing';

import { PortfolioSnapshotsService } from './portfolio-snapshots.service';

describe('PortfolioSnapshotsService', () => {
  let service: PortfolioSnapshotsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PortfolioSnapshotsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
