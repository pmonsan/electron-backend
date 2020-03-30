import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDetailTransaction } from 'app/shared/model/detail-transaction.model';

@Component({
  selector: 'jhi-detail-transaction-detail',
  templateUrl: './detail-transaction-detail.component.html'
})
export class DetailTransactionDetailComponent implements OnInit {
  detailTransaction: IDetailTransaction;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ detailTransaction }) => {
      this.detailTransaction = detailTransaction;
    });
  }

  previousState() {
    window.history.back();
  }
}
