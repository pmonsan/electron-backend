import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransactionStatus } from 'app/shared/model/transaction-status.model';

@Component({
  selector: 'jhi-transaction-status-detail',
  templateUrl: './transaction-status-detail.component.html'
})
export class TransactionStatusDetailComponent implements OnInit {
  transactionStatus: ITransactionStatus;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transactionStatus }) => {
      this.transactionStatus = transactionStatus;
    });
  }

  previousState() {
    window.history.back();
  }
}
