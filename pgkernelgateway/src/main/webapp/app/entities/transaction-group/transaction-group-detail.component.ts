import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransactionGroup } from 'app/shared/model/transaction-group.model';

@Component({
  selector: 'jhi-transaction-group-detail',
  templateUrl: './transaction-group-detail.component.html'
})
export class TransactionGroupDetailComponent implements OnInit {
  transactionGroup: ITransactionGroup;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transactionGroup }) => {
      this.transactionGroup = transactionGroup;
    });
  }

  previousState() {
    window.history.back();
  }
}
