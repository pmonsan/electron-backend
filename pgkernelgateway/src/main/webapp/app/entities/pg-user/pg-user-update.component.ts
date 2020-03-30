import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IPgUser, PgUser } from 'app/shared/model/pg-user.model';
import { PgUserService } from './pg-user.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile';

@Component({
  selector: 'jhi-pg-user-update',
  templateUrl: './pg-user-update.component.html'
})
export class PgUserUpdateComponent implements OnInit {
  isSaving: boolean;

  userprofiles: IUserProfile[];

  editForm = this.fb.group({
    id: [],
    username: [null, [Validators.required, Validators.maxLength(50)]],
    email: [null, [Validators.required, Validators.pattern('^[^@s]+@[^@s]+.[^@s]+$')]],
    firstname: [null, [Validators.maxLength(50)]],
    name: [null, [Validators.maxLength(50)]],
    msisdn: [null, [Validators.maxLength(20)]],
    creationDate: [null, [Validators.required]],
    updateDate: [],
    userProfileId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected pgUserService: PgUserService,
    protected userProfileService: UserProfileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pgUser }) => {
      this.updateForm(pgUser);
    });
    this.userProfileService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUserProfile[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUserProfile[]>) => response.body)
      )
      .subscribe((res: IUserProfile[]) => (this.userprofiles = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(pgUser: IPgUser) {
    this.editForm.patchValue({
      id: pgUser.id,
      username: pgUser.username,
      email: pgUser.email,
      firstname: pgUser.firstname,
      name: pgUser.name,
      msisdn: pgUser.msisdn,
      creationDate: pgUser.creationDate != null ? pgUser.creationDate.format(DATE_TIME_FORMAT) : null,
      updateDate: pgUser.updateDate != null ? pgUser.updateDate.format(DATE_TIME_FORMAT) : null,
      userProfileId: pgUser.userProfileId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pgUser = this.createFromForm();
    if (pgUser.id !== undefined) {
      this.subscribeToSaveResponse(this.pgUserService.update(pgUser));
    } else {
      this.subscribeToSaveResponse(this.pgUserService.create(pgUser));
    }
  }

  private createFromForm(): IPgUser {
    return {
      ...new PgUser(),
      id: this.editForm.get(['id']).value,
      username: this.editForm.get(['username']).value,
      email: this.editForm.get(['email']).value,
      firstname: this.editForm.get(['firstname']).value,
      name: this.editForm.get(['name']).value,
      msisdn: this.editForm.get(['msisdn']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      updateDate:
        this.editForm.get(['updateDate']).value != null ? moment(this.editForm.get(['updateDate']).value, DATE_TIME_FORMAT) : undefined,
      userProfileId: this.editForm.get(['userProfileId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPgUser>>) {
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
