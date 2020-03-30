import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPgTransactionType2 } from 'app/shared/model/pg-transaction-type-2.model';

@Component({
  selector: 'jhi-pg-transaction-type-2-detail',
  templateUrl: './pg-transaction-type-2-detail.component.html'
})
export class PgTransactionType2DetailComponent implements OnInit {
  pgTransactionType2: IPgTransactionType2;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgTransactionType2 }) => {
      this.pgTransactionType2 = pgTransactionType2;
    });
  }

  previousState() {
    window.history.back();
  }
}
