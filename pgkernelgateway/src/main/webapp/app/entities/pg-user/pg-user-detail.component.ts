import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPgUser } from 'app/shared/model/pg-user.model';

@Component({
  selector: 'jhi-pg-user-detail',
  templateUrl: './pg-user-detail.component.html'
})
export class PgUserDetailComponent implements OnInit {
  pgUser: IPgUser;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgUser }) => {
      this.pgUser = pgUser;
    });
  }

  previousState() {
    window.history.back();
  }
}
