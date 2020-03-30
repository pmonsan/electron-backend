import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccountType } from 'app/shared/model/account-type.model';

@Component({
  selector: 'jhi-account-type-detail',
  templateUrl: './account-type-detail.component.html'
})
export class AccountTypeDetailComponent implements OnInit {
  accountType: IAccountType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ accountType }) => {
      this.accountType = accountType;
    });
  }

  previousState() {
    window.history.back();
  }
}
