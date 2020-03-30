import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransactionProperty } from 'app/shared/model/transaction-property.model';

@Component({
  selector: 'jhi-transaction-property-detail',
  templateUrl: './transaction-property-detail.component.html'
})
export class TransactionPropertyDetailComponent implements OnInit {
  transactionProperty: ITransactionProperty;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transactionProperty }) => {
      this.transactionProperty = transactionProperty;
    });
  }

  previousState() {
    window.history.back();
  }
}
