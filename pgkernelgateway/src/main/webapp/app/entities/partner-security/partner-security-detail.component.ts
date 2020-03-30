import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartnerSecurity } from 'app/shared/model/partner-security.model';

@Component({
  selector: 'jhi-partner-security-detail',
  templateUrl: './partner-security-detail.component.html'
})
export class PartnerSecurityDetailComponent implements OnInit {
  partnerSecurity: IPartnerSecurity;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ partnerSecurity }) => {
      this.partnerSecurity = partnerSecurity;
    });
  }

  previousState() {
    window.history.back();
  }
}
