import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPgDetailMessage } from 'app/shared/model/pg-detail-message.model';

@Component({
  selector: 'jhi-pg-detail-message-detail',
  templateUrl: './pg-detail-message-detail.component.html'
})
export class PgDetailMessageDetailComponent implements OnInit {
  pgDetailMessage: IPgDetailMessage;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgDetailMessage }) => {
      this.pgDetailMessage = pgDetailMessage;
    });
  }

  previousState() {
    window.history.back();
  }
}
