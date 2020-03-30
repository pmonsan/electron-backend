import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConnector } from 'app/shared/model/connector.model';

@Component({
  selector: 'jhi-connector-detail',
  templateUrl: './connector-detail.component.html'
})
export class ConnectorDetailComponent implements OnInit {
  connector: IConnector;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ connector }) => {
      this.connector = connector;
    });
  }

  previousState() {
    window.history.back();
  }
}
