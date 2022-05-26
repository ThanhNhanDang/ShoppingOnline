import { TestBed } from '@angular/core/testing';

import { GuardCanActivateGuard } from './guard-can-activate.guard';

describe('GuardCanActivateGuard', () => {
  let guard: GuardCanActivateGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(GuardCanActivateGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});

