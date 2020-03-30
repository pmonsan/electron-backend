import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBeneficiaryRelationship } from 'app/shared/model/beneficiary-relationship.model';

@Component({
  selector: 'jhi-beneficiary-relationship-detail',
  templateUrl: './beneficiary-relationship-detail.component.html'
})
export class BeneficiaryRelationshipDetailComponent implements OnInit {
  beneficiaryRelationship: IBeneficiaryRelationship;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ beneficiaryRelationship }) => {
      this.beneficiaryRelationship = beneficiaryRelationship;
    });
  }

  previousState() {
    window.history.back();
  }
}
