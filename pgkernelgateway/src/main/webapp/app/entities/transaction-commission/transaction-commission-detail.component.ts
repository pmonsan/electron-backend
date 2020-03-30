import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransactionCommission } from 'app/shared/model/transaction-commission.model';

@Component({
  selector: 'jhi-transaction-commission-detail',
  templateUrl: './transaction-commission-detail.component.html'
})
export class TransactionCommissionDetailComponent implements OnInit {
  transactionCommission: ITransactionCommission;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transactionCommission }) => {
      this.transactionCommission = transactionCommission;
    });
  }

  previousState() {
    window.history.back();
  }
}
