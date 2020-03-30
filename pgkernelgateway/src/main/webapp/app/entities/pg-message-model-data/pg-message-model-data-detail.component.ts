import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPgMessageModelData } from 'app/shared/model/pg-message-model-data.model';

@Component({
  selector: 'jhi-pg-message-model-data-detail',
  templateUrl: './pg-message-model-data-detail.component.html'
})
export class PgMessageModelDataDetailComponent implements OnInit {
  pgMessageModelData: IPgMessageModelData;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgMessageModelData }) => {
      this.pgMessageModelData = pgMessageModelData;
    });
  }

  previousState() {
    window.history.back();
  }
}
