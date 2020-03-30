import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonType } from 'app/shared/model/person-type.model';

@Component({
  selector: 'jhi-person-type-detail',
  templateUrl: './person-type-detail.component.html'
})
export class PersonTypeDetailComponent implements OnInit {
  personType: IPersonType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ personType }) => {
      this.personType = personType;
    });
  }

  previousState() {
    window.history.back();
  }
}
