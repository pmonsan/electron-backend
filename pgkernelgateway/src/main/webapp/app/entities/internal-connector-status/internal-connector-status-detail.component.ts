import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInternalConnectorStatus } from 'app/shared/model/internal-connector-status.model';

@Component({
  selector: 'jhi-internal-connector-status-detail',
  templateUrl: './internal-connector-status-detail.component.html'
})
export class InternalConnectorStatusDetailComponent implements OnInit {
  internalConnectorStatus: IInternalConnectorStatus;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ internalConnectorStatus }) => {
      this.internalConnectorStatus = internalConnectorStatus;
    });
  }

  previousState() {
    window.history.back();
  }
}
