import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartnerType } from 'app/shared/model/partner-type.model';

@Component({
  selector: 'jhi-partner-type-detail',
  templateUrl: './partner-type-detail.component.html'
})
export class PartnerTypeDetailComponent implements OnInit {
  partnerType: IPartnerType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ partnerType }) => {
      this.partnerType = partnerType;
    });
  }

  previousState() {
    window.history.back();
  }
}
