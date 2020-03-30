import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContractOpposition } from 'app/shared/model/contract-opposition.model';

@Component({
  selector: 'jhi-contract-opposition-detail',
  templateUrl: './contract-opposition-detail.component.html'
})
export class ContractOppositionDetailComponent implements OnInit {
  contractOpposition: IContractOpposition;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contractOpposition }) => {
      this.contractOpposition = contractOpposition;
    });
  }

  previousState() {
    window.history.back();
  }
}
