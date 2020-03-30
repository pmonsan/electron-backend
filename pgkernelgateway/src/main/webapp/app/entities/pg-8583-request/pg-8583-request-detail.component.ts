import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPg8583Request } from 'app/shared/model/pg-8583-request.model';

@Component({
  selector: 'jhi-pg-8583-request-detail',
  templateUrl: './pg-8583-request-detail.component.html'
})
export class Pg8583RequestDetailComponent implements OnInit {
  pg8583Request: IPg8583Request;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pg8583Request }) => {
      this.pg8583Request = pg8583Request;
    });
  }

  previousState() {
    window.history.back();
  }
}
