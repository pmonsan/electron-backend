import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICustomerType } from 'app/shared/model/customer-type.model';

@Component({
  selector: 'jhi-customer-type-detail',
  templateUrl: './customer-type-detail.component.html'
})
export class CustomerTypeDetailComponent implements OnInit {
  customerType: ICustomerType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ customerType }) => {
      this.customerType = customerType;
    });
  }

  previousState() {
    window.history.back();
  }
}
