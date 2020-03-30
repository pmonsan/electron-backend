import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPgApplication } from 'app/shared/model/pg-application.model';

@Component({
  selector: 'jhi-pg-application-detail',
  templateUrl: './pg-application-detail.component.html'
})
export class PgApplicationDetailComponent implements OnInit {
  pgApplication: IPgApplication;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgApplication }) => {
      this.pgApplication = pgApplication;
    });
  }

  previousState() {
    window.history.back();
  }
}
