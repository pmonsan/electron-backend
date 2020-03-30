import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConnectorType } from 'app/shared/model/connector-type.model';

@Component({
  selector: 'jhi-connector-type-detail',
  templateUrl: './connector-type-detail.component.html'
})
export class ConnectorTypeDetailComponent implements OnInit {
  connectorType: IConnectorType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ connectorType }) => {
      this.connectorType = connectorType;
    });
  }

  previousState() {
    window.history.back();
  }
}
