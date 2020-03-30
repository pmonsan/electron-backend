import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserProfile } from 'app/shared/model/user-profile.model';

@Component({
  selector: 'jhi-user-profile-detail',
  templateUrl: './user-profile-detail.component.html'
})
export class UserProfileDetailComponent implements OnInit {
  userProfile: IUserProfile;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userProfile }) => {
      this.userProfile = userProfile;
    });
  }

  previousState() {
    window.history.back();
  }
}
