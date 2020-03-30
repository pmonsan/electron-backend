import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPgChannelAuthorized } from 'app/shared/model/pg-channel-authorized.model';

@Component({
  selector: 'jhi-pg-channel-authorized-detail',
  templateUrl: './pg-channel-authorized-detail.component.html'
})
export class PgChannelAuthorizedDetailComponent implements OnInit {
  pgChannelAuthorized: IPgChannelAuthorized;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgChannelAuthorized }) => {
      this.pgChannelAuthorized = pgChannelAuthorized;
    });
  }

  previousState() {
    window.history.back();
  }
}
