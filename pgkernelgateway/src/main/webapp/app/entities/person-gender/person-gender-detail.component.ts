import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonGender } from 'app/shared/model/person-gender.model';

@Component({
  selector: 'jhi-person-gender-detail',
  templateUrl: './person-gender-detail.component.html'
})
export class PersonGenderDetailComponent implements OnInit {
  personGender: IPersonGender;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ personGender }) => {
      this.personGender = personGender;
    });
  }

  previousState() {
    window.history.back();
  }
}
