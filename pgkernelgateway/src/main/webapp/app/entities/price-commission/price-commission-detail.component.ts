import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPriceCommission } from 'app/shared/model/price-commission.model';

@Component({
  selector: 'jhi-price-commission-detail',
  templateUrl: './price-commission-detail.component.html'
})
export class PriceCommissionDetailComponent implements OnInit {
  priceCommission: IPriceCommission;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ priceCommission }) => {
      this.priceCommission = priceCommission;
    });
  }

  previousState() {
    window.history.back();
  }
}
