import { TestBed } from '@angular/core/testing';

import { EchartServiceService } from './echart-service.service';

describe('EchartServiceService', () => {
  let service: EchartServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EchartServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
