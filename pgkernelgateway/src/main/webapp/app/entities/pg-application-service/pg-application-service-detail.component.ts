import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPgApplicationService } from 'app/shared/model/pg-application-service.model';

@Component({
  selector: 'jhi-pg-application-service-detail',
  templateUrl: './pg-application-service-detail.component.html'
})
export class PgApplicationServiceDetailComponent implements OnInit {
  pgApplicationService: IPgApplicationService;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgApplicationService }) => {
      this.pgApplicationService = pgApplicationService;
    });
  }

  previousState() {
    window.history.back();
  }
}
