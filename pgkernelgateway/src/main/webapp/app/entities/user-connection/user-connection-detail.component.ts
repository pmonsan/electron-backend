import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserConnection } from 'app/shared/model/user-connection.model';

@Component({
  selector: 'jhi-user-connection-detail',
  templateUrl: './user-connection-detail.component.html'
})
export class UserConnectionDetailComponent implements OnInit {
  userConnection: IUserConnection;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userConnection }) => {
      this.userConnection = userConnection;
    });
  }

  previousState() {
    window.history.back();
  }
}
