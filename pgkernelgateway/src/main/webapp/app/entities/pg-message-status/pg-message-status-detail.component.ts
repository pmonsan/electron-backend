import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPgMessageStatus } from 'app/shared/model/pg-message-status.model';

@Component({
  selector: 'jhi-pg-message-status-detail',
  templateUrl: './pg-message-status-detail.component.html'
})
export class PgMessageStatusDetailComponent implements OnInit {
  pgMessageStatus: IPgMessageStatus;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgMessageStatus }) => {
      this.pgMessageStatus = pgMessageStatus;
    });
  }

  previousState() {
    window.history.back();
  }
}
