import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOperationStatus } from 'app/shared/model/operation-status.model';

@Component({
  selector: 'jhi-operation-status-detail',
  templateUrl: './operation-status-detail.component.html'
})
export class OperationStatusDetailComponent implements OnInit {
  operationStatus: IOperationStatus;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ operationStatus }) => {
      this.operationStatus = operationStatus;
    });
  }

  previousState() {
    window.history.back();
  }
}
