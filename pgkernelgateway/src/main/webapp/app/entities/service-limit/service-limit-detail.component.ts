import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceLimit } from 'app/shared/model/service-limit.model';

@Component({
  selector: 'jhi-service-limit-detail',
  templateUrl: './service-limit-detail.component.html'
})
export class ServiceLimitDetailComponent implements OnInit {
  serviceLimit: IServiceLimit;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceLimit }) => {
      this.serviceLimit = serviceLimit;
    });
  }

  previousState() {
    window.history.back();
  }
}
