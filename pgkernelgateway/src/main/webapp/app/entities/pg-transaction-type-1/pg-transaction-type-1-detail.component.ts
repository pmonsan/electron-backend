import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPgTransactionType1 } from 'app/shared/model/pg-transaction-type-1.model';

@Component({
  selector: 'jhi-pg-transaction-type-1-detail',
  templateUrl: './pg-transaction-type-1-detail.component.html'
})
export class PgTransactionType1DetailComponent implements OnInit {
  pgTransactionType1: IPgTransactionType1;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgTransactionType1 }) => {
      this.pgTransactionType1 = pgTransactionType1;
    });
  }

  previousState() {
    window.history.back();
  }
}
