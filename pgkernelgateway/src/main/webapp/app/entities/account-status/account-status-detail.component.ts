import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccountStatus } from 'app/shared/model/account-status.model';

@Component({
  selector: 'jhi-account-status-detail',
  templateUrl: './account-status-detail.component.html'
})
export class AccountStatusDetailComponent implements OnInit {
  accountStatus: IAccountStatus;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ accountStatus }) => {
      this.accountStatus = accountStatus;
    });
  }

  previousState() {
    window.history.back();
  }
}
