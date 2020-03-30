import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICustomerSubscription } from 'app/shared/model/customer-subscription.model';

@Component({
  selector: 'jhi-customer-subscription-detail',
  templateUrl: './customer-subscription-detail.component.html'
})
export class CustomerSubscriptionDetailComponent implements OnInit {
  customerSubscription: ICustomerSubscription;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ customerSubscription }) => {
      this.customerSubscription = customerSubscription;
    });
  }

  previousState() {
    window.history.back();
  }
}
