import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILoanInstalmentStatus } from 'app/shared/model/loan-instalment-status.model';

@Component({
  selector: 'jhi-loan-instalment-status-detail',
  templateUrl: './loan-instalment-status-detail.component.html'
})
export class LoanInstalmentStatusDetailComponent implements OnInit {
  loanInstalmentStatus: ILoanInstalmentStatus;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ loanInstalmentStatus }) => {
      this.loanInstalmentStatus = loanInstalmentStatus;
    });
  }

  previousState() {
    window.history.back();
  }
}
