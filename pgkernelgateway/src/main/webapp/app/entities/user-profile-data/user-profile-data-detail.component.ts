import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserProfileData } from 'app/shared/model/user-profile-data.model';

@Component({
  selector: 'jhi-user-profile-data-detail',
  templateUrl: './user-profile-data-detail.component.html'
})
export class UserProfileDataDetailComponent implements OnInit {
  userProfileData: IUserProfileData;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userProfileData }) => {
      this.userProfileData = userProfileData;
    });
  }

  previousState() {
    window.history.back();
  }
}
