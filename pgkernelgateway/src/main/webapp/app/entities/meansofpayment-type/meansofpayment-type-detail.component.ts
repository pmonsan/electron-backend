import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMeansofpaymentType } from 'app/shared/model/meansofpayment-type.model';

@Component({
  selector: 'jhi-meansofpayment-type-detail',
  templateUrl: './meansofpayment-type-detail.component.html'
})
export class MeansofpaymentTypeDetailComponent implements OnInit {
  meansofpaymentType: IMeansofpaymentType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ meansofpaymentType }) => {
      this.meansofpaymentType = meansofpaymentType;
    });
  }

  previousState() {
    window.history.back();
  }
}
