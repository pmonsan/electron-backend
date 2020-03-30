import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPricePlan } from 'app/shared/model/price-plan.model';

@Component({
  selector: 'jhi-price-plan-detail',
  templateUrl: './price-plan-detail.component.html'
})
export class PricePlanDetailComponent implements OnInit {
  pricePlan: IPricePlan;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pricePlan }) => {
      this.pricePlan = pricePlan;
    });
  }

  previousState() {
    window.history.back();
  }
}
