import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILoanInstalment } from 'app/shared/model/loan-instalment.model';

@Component({
  selector: 'jhi-loan-instalment-detail',
  templateUrl: './loan-instalment-detail.component.html'
})
export class LoanInstalmentDetailComponent implements OnInit {
  loanInstalment: ILoanInstalment;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ loanInstalment }) => {
      this.loanInstalment = loanInstalment;
    });
  }

  previousState() {
    window.history.back();
  }
}
