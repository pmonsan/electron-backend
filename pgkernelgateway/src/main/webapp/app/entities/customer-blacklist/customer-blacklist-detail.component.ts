import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICustomerBlacklist } from 'app/shared/model/customer-blacklist.model';

@Component({
  selector: 'jhi-customer-blacklist-detail',
  templateUrl: './customer-blacklist-detail.component.html'
})
export class CustomerBlacklistDetailComponent implements OnInit {
  customerBlacklist: ICustomerBlacklist;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ customerBlacklist }) => {
      this.customerBlacklist = customerBlacklist;
    });
  }

  previousState() {
    window.history.back();
  }
}
