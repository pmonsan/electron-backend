import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICustomerLimit } from 'app/shared/model/customer-limit.model';

@Component({
  selector: 'jhi-customer-limit-detail',
  templateUrl: './customer-limit-detail.component.html'
})
export class CustomerLimitDetailComponent implements OnInit {
  customerLimit: ICustomerLimit;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ customerLimit }) => {
      this.customerLimit = customerLimit;
    });
  }

  previousState() {
    window.history.back();
  }
}
