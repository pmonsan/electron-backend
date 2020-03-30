import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPgModule } from 'app/shared/model/pg-module.model';

@Component({
  selector: 'jhi-pg-module-detail',
  templateUrl: './pg-module-detail.component.html'
})
export class PgModuleDetailComponent implements OnInit {
  pgModule: IPgModule;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgModule }) => {
      this.pgModule = pgModule;
    });
  }

  previousState() {
    window.history.back();
  }
}
