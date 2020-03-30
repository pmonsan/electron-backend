import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartner } from 'app/shared/model/partner.model';

@Component({
  selector: 'jhi-partner-detail',
  templateUrl: './partner-detail.component.html'
})
export class PartnerDetailComponent implements OnInit {
  partner: IPartner;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ partner }) => {
      this.partner = partner;
    });
  }

  previousState() {
    window.history.back();
  }
}
