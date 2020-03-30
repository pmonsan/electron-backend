import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFeature } from 'app/shared/model/feature.model';

@Component({
  selector: 'jhi-feature-detail',
  templateUrl: './feature-detail.component.html'
})
export class FeatureDetailComponent implements OnInit {
  feature: IFeature;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ feature }) => {
      this.feature = feature;
    });
  }

  previousState() {
    window.history.back();
  }
}
