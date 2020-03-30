import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBeneficiary } from 'app/shared/model/beneficiary.model';

@Component({
  selector: 'jhi-beneficiary-detail',
  templateUrl: './beneficiary-detail.component.html'
})
export class BeneficiaryDetailComponent implements OnInit {
  beneficiary: IBeneficiary;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ beneficiary }) => {
      this.beneficiary = beneficiary;
    });
  }

  previousState() {
    window.history.back();
  }
}
