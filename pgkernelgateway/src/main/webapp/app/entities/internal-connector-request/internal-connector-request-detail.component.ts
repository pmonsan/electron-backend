import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInternalConnectorRequest } from 'app/shared/model/internal-connector-request.model';

@Component({
  selector: 'jhi-internal-connector-request-detail',
  templateUrl: './internal-connector-request-detail.component.html'
})
export class InternalConnectorRequestDetailComponent implements OnInit {
  internalConnectorRequest: IInternalConnectorRequest;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ internalConnectorRequest }) => {
      this.internalConnectorRequest = internalConnectorRequest;
    });
  }

  previousState() {
    window.history.back();
  }
}
