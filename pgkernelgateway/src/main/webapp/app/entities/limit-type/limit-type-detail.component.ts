import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILimitType } from 'app/shared/model/limit-type.model';

@Component({
  selector: 'jhi-limit-type-detail',
  templateUrl: './limit-type-detail.component.html'
})
export class LimitTypeDetailComponent implements OnInit {
  limitType: ILimitType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ limitType }) => {
      this.limitType = limitType;
    });
  }

  previousState() {
    window.history.back();
  }
}
