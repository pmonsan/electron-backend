import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPgAccount } from 'app/shared/model/pg-account.model';

@Component({
  selector: 'jhi-pg-account-detail',
  templateUrl: './pg-account-detail.component.html'
})
export class PgAccountDetailComponent implements OnInit {
  pgAccount: IPgAccount;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgAccount }) => {
      this.pgAccount = pgAccount;
    });
  }

  previousState() {
    window.history.back();
  }
}
