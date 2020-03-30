import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILimitMeasure } from 'app/shared/model/limit-measure.model';

@Component({
  selector: 'jhi-limit-measure-detail',
  templateUrl: './limit-measure-detail.component.html'
})
export class LimitMeasureDetailComponent implements OnInit {
  limitMeasure: ILimitMeasure;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ limitMeasure }) => {
      this.limitMeasure = limitMeasure;
    });
  }

  previousState() {
    window.history.back();
  }
}
