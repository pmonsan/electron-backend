import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPeriodicity } from 'app/shared/model/periodicity.model';

@Component({
  selector: 'jhi-periodicity-detail',
  templateUrl: './periodicity-detail.component.html'
})
export class PeriodicityDetailComponent implements OnInit {
  periodicity: IPeriodicity;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ periodicity }) => {
      this.periodicity = periodicity;
    });
  }

  previousState() {
    window.history.back();
  }
}
