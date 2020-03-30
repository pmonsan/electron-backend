import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceIntegration } from 'app/shared/model/service-integration.model';

@Component({
  selector: 'jhi-service-integration-detail',
  templateUrl: './service-integration-detail.component.html'
})
export class ServiceIntegrationDetailComponent implements OnInit {
  serviceIntegration: IServiceIntegration;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceIntegration }) => {
      this.serviceIntegration = serviceIntegration;
    });
  }

  previousState() {
    window.history.back();
  }
}
