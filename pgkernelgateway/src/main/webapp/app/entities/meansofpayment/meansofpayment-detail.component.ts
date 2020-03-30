import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMeansofpayment } from 'app/shared/model/meansofpayment.model';

@Component({
  selector: 'jhi-meansofpayment-detail',
  templateUrl: './meansofpayment-detail.component.html'
})
export class MeansofpaymentDetailComponent implements OnInit {
  meansofpayment: IMeansofpayment;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ meansofpayment }) => {
      this.meansofpayment = meansofpayment;
    });
  }

  previousState() {
    window.history.back();
  }
}
