import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPgData } from 'app/shared/model/pg-data.model';

@Component({
  selector: 'jhi-pg-data-detail',
  templateUrl: './pg-data-detail.component.html'
})
export class PgDataDetailComponent implements OnInit {
  pgData: IPgData;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgData }) => {
      this.pgData = pgData;
    });
  }

  previousState() {
    window.history.back();
  }
}
