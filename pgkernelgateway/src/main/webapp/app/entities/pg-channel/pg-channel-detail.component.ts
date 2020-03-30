import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPgChannel } from 'app/shared/model/pg-channel.model';

@Component({
  selector: 'jhi-pg-channel-detail',
  templateUrl: './pg-channel-detail.component.html'
})
export class PgChannelDetailComponent implements OnInit {
  pgChannel: IPgChannel;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgChannel }) => {
      this.pgChannel = pgChannel;
    });
  }

  previousState() {
    window.history.back();
  }
}
