import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IForex } from 'app/shared/model/forex.model';

@Component({
  selector: 'jhi-forex-detail',
  templateUrl: './forex-detail.component.html'
})
export class ForexDetailComponent implements OnInit {
  forex: IForex;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ forex }) => {
      this.forex = forex;
    });
  }

  previousState() {
    window.history.back();
  }
}
