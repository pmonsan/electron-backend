import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDetailContract } from 'app/shared/model/detail-contract.model';

@Component({
  selector: 'jhi-detail-contract-detail',
  templateUrl: './detail-contract-detail.component.html'
})
export class DetailContractDetailComponent implements OnInit {
  detailContract: IDetailContract;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ detailContract }) => {
      this.detailContract = detailContract;
    });
  }

  previousState() {
    window.history.back();
  }
}
