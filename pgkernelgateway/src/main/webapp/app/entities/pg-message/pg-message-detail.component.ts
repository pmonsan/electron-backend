import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPgMessage } from 'app/shared/model/pg-message.model';

@Component({
  selector: 'jhi-pg-message-detail',
  templateUrl: './pg-message-detail.component.html'
})
export class PgMessageDetailComponent implements OnInit {
  pgMessage: IPgMessage;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgMessage }) => {
      this.pgMessage = pgMessage;
    });
  }

  previousState() {
    window.history.back();
  }
}
