import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPg8583Callback } from 'app/shared/model/pg-8583-callback.model';

@Component({
  selector: 'jhi-pg-8583-callback-detail',
  templateUrl: './pg-8583-callback-detail.component.html'
})
export class Pg8583CallbackDetailComponent implements OnInit {
  pg8583Callback: IPg8583Callback;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pg8583Callback }) => {
      this.pg8583Callback = pg8583Callback;
    });
  }

  previousState() {
    window.history.back();
  }
}
