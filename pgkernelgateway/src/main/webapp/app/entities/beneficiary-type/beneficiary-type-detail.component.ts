import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBeneficiaryType } from 'app/shared/model/beneficiary-type.model';

@Component({
  selector: 'jhi-beneficiary-type-detail',
  templateUrl: './beneficiary-type-detail.component.html'
})
export class BeneficiaryTypeDetailComponent implements OnInit {
  beneficiaryType: IBeneficiaryType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ beneficiaryType }) => {
      this.beneficiaryType = beneficiaryType;
    });
  }

  previousState() {
    window.history.back();
  }
}
