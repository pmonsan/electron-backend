import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccountFeature } from 'app/shared/model/account-feature.model';

@Component({
  selector: 'jhi-account-feature-detail',
  templateUrl: './account-feature-detail.component.html'
})
export class AccountFeatureDetailComponent implements OnInit {
  accountFeature: IAccountFeature;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ accountFeature }) => {
      this.accountFeature = accountFeature;
    });
  }

  previousState() {
    window.history.back();
  }
}
