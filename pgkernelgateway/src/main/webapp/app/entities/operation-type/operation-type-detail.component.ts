import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOperationType } from 'app/shared/model/operation-type.model';

@Component({
  selector: 'jhi-operation-type-detail',
  templateUrl: './operation-type-detail.component.html'
})
export class OperationTypeDetailComponent implements OnInit {
  operationType: IOperationType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ operationType }) => {
      this.operationType = operationType;
    });
  }

  previousState() {
    window.history.back();
  }
}
