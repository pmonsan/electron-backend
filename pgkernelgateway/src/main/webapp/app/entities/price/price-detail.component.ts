import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrice } from 'app/shared/model/price.model';

@Component({
  selector: 'jhi-price-detail',
  templateUrl: './price-detail.component.html'
})
export class PriceDetailComponent implements OnInit {
  price: IPrice;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ price }) => {
      this.price = price;
    });
  }

  previousState() {
    window.history.back();
  }
}
