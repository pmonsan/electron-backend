import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPgBatch } from 'app/shared/model/pg-batch.model';

@Component({
  selector: 'jhi-pg-batch-detail',
  templateUrl: './pg-batch-detail.component.html'
})
export class PgBatchDetailComponent implements OnInit {
  pgBatch: IPgBatch;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgBatch }) => {
      this.pgBatch = pgBatch;
    });
  }

  previousState() {
    window.history.back();
  }
}
