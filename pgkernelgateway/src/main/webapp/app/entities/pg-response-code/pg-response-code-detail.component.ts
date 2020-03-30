import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPgResponseCode } from 'app/shared/model/pg-response-code.model';

@Component({
  selector: 'jhi-pg-response-code-detail',
  templateUrl: './pg-response-code-detail.component.html'
})
export class PgResponseCodeDetailComponent implements OnInit {
  pgResponseCode: IPgResponseCode;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgResponseCode }) => {
      this.pgResponseCode = pgResponseCode;
    });
  }

  previousState() {
    window.history.back();
  }
}
