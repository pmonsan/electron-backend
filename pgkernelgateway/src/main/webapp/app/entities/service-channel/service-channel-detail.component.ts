import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceChannel } from 'app/shared/model/service-channel.model';

@Component({
  selector: 'jhi-service-channel-detail',
  templateUrl: './service-channel-detail.component.html'
})
export class ServiceChannelDetailComponent implements OnInit {
  serviceChannel: IServiceChannel;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceChannel }) => {
      this.serviceChannel = serviceChannel;
    });
  }

  previousState() {
    window.history.back();
  }
}
