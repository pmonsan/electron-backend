import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransactionPrice } from 'app/shared/model/transaction-price.model';

@Component({
  selector: 'jhi-transaction-price-detail',
  templateUrl: './transaction-price-detail.component.html'
})
export class TransactionPriceDetailComponent implements OnInit {
  transactionPrice: ITransactionPrice;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transactionPrice }) => {
      this.transactionPrice = transactionPrice;
    });
  }

  previousState() {
    window.history.back();
  }
}
