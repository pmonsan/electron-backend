import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPgMessageModel } from 'app/shared/model/pg-message-model.model';

@Component({
  selector: 'jhi-pg-message-model-detail',
  templateUrl: './pg-message-model-detail.component.html'
})
export class PgMessageModelDetailComponent implements OnInit {
  pgMessageModel: IPgMessageModel;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgMessageModel }) => {
      this.pgMessageModel = pgMessageModel;
    });
  }

  previousState() {
    window.history.back();
  }
}
