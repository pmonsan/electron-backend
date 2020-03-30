import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContract } from 'app/shared/model/contract.model';

@Component({
  selector: 'jhi-contract-detail',
  templateUrl: './contract-detail.component.html'
})
export class ContractDetailComponent implements OnInit {
  contract: IContract;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contract }) => {
      this.contract = contract;
    });
  }

  previousState() {
    window.history.back();
  }
}
