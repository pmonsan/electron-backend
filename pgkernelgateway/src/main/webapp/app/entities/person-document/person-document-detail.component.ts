import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonDocument } from 'app/shared/model/person-document.model';

@Component({
  selector: 'jhi-person-document-detail',
  templateUrl: './person-document-detail.component.html'
})
export class PersonDocumentDetailComponent implements OnInit {
  personDocument: IPersonDocument;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ personDocument }) => {
      this.personDocument = personDocument;
    });
  }

  previousState() {
    window.history.back();
  }
}
