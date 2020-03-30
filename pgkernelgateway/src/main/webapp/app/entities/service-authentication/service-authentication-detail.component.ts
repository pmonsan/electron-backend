import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceAuthentication } from 'app/shared/model/service-authentication.model';

@Component({
  selector: 'jhi-service-authentication-detail',
  templateUrl: './service-authentication-detail.component.html'
})
export class ServiceAuthenticationDetailComponent implements OnInit {
  serviceAuthentication: IServiceAuthentication;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceAuthentication }) => {
      this.serviceAuthentication = serviceAuthentication;
    });
  }

  previousState() {
    window.history.back();
  }
}
