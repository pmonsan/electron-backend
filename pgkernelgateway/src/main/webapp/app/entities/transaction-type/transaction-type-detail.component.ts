import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransactionType } from 'app/shared/model/transaction-type.model';

@Component({
  selector: 'jhi-transaction-type-detail',
  templateUrl: './transaction-type-detail.component.html'
})
export class TransactionTypeDetailComponent implements OnInit {
  transactionType: ITransactionType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transactionType }) => {
      this.transactionType = transactionType;
    });
  }

  previousState() {
    window.history.back();
  }
}
