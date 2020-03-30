import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IUserProfileData, UserProfileData } from 'app/shared/model/user-profile-data.model';
import { UserProfileDataService } from './user-profile-data.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile';

@Component({
  selector: 'jhi-user-profile-data-update',
  templateUrl: './user-profile-data-update.component.html'
})
export class UserProfileDataUpdateComponent implements OnInit {
  isSaving: boolean;

  userprofiles: IUserProfile[];

  editForm = this.fb.group({
    id: [],
    active: [null, [Validators.required]],
    pgDataCode: [null, [Validators.maxLength(5)]],
    userProfileId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected userProfileDataService: UserProfileDataService,
    protected userProfileService: UserProfileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ userProfileData }) => {
      this.updateForm(userProfileData);
    });
    this.userProfileService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUserProfile[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUserProfile[]>) => response.body)
      )
      .subscribe((res: IUserProfile[]) => (this.userprofiles = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(userProfileData: IUserProfileData) {
    this.editForm.patchValue({
      id: userProfileData.id,
      active: userProfileData.active,
      pgDataCode: userProfileData.pgDataCode,
      userProfileId: userProfileData.userProfileId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const userProfileData = this.createFromForm();
    if (userProfileData.id !== undefined) {
      this.subscribeToSaveResponse(this.userProfileDataService.update(userProfileData));
    } else {
      this.subscribeToSaveResponse(this.userProfileDataService.create(userProfileData));
    }
  }

  private createFromForm(): IUserProfileData {
    return {
      ...new UserProfileData(),
      id: this.editForm.get(['id']).value,
      active: this.editForm.get(['active']).value,
      pgDataCode: this.editForm.get(['pgDataCode']).value,
      userProfileId: this.editForm.get(['userProfileId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserProfileData>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackUserProfileById(index: number, item: IUserProfile) {
    return item.id;
  }
}
