import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPg8583Status } from 'app/shared/model/pg-8583-status.model';

@Component({
  selector: 'jhi-pg-8583-status-detail',
  templateUrl: './pg-8583-status-detail.component.html'
})
export class Pg8583StatusDetailComponent implements OnInit {
  pg8583Status: IPg8583Status;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pg8583Status }) => {
      this.pg8583Status = pg8583Status;
    });
  }

  previousState() {
    window.history.back();
  }
}
