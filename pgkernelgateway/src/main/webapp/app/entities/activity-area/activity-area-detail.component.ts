import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActivityArea } from 'app/shared/model/activity-area.model';

@Component({
  selector: 'jhi-activity-area-detail',
  templateUrl: './activity-area-detail.component.html'
})
export class ActivityAreaDetailComponent implements OnInit {
  activityArea: IActivityArea;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ activityArea }) => {
      this.activityArea = activityArea;
    });
  }

  previousState() {
    window.history.back();
  }
}
