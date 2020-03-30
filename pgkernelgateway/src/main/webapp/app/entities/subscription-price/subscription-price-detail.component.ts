import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubscriptionPrice } from 'app/shared/model/subscription-price.model';

@Component({
  selector: 'jhi-subscription-price-detail',
  templateUrl: './subscription-price-detail.component.html'
})
export class SubscriptionPriceDetailComponent implements OnInit {
  subscriptionPrice: ISubscriptionPrice;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ subscriptionPrice }) => {
      this.subscriptionPrice = subscriptionPrice;
    });
  }

  previousState() {
    window.history.back();
  }
}
