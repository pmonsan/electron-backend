import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPgService } from 'app/shared/model/pg-service.model';

@Component({
  selector: 'jhi-pg-service-detail',
  templateUrl: './pg-service-detail.component.html'
})
export class PgServiceDetailComponent implements OnInit {
  pgService: IPgService;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgService }) => {
      this.pgService = pgService;
    });
  }

  previousState() {
    window.history.back();
  }
}
