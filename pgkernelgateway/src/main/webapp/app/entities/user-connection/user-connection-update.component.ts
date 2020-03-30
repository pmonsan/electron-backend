import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IUserConnection, UserConnection } from 'app/shared/model/user-connection.model';
import { UserConnectionService } from './user-connection.service';
import { IPgUser } from 'app/shared/model/pg-user.model';
import { PgUserService } from 'app/entities/pg-user';

@Component({
  selector: 'jhi-user-connection-update',
  templateUrl: './user-connection-update.component.html'
})
export class UserConnectionUpdateComponent implements OnInit {
  isSaving: boolean;

  pgusers: IPgUser[];

  editForm = this.fb.group({
    id: [],
    loginDate: [null, [Validators.required]],
    logoutDate: [],
    userId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected userConnectionService: UserConnectionService,
    protected pgUserService: PgUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ userConnection }) => {
      this.updateForm(userConnection);
    });
    this.pgUserService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPgUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPgUser[]>) => response.body)
      )
      .subscribe((res: IPgUser[]) => (this.pgusers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(userConnection: IUserConnection) {
    this.editForm.patchValue({
      id: userConnection.id,
      loginDate: userConnection.loginDate != null ? userConnection.loginDate.format(DATE_TIME_FORMAT) : null,
      logoutDate: userConnection.logoutDate != null ? userConnection.logoutDate.format(DATE_TIME_FORMAT) : null,
      userId: userConnection.userId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const userConnection = this.createFromForm();
    if (userConnection.id !== undefined) {
      this.subscribeToSaveResponse(this.userConnectionService.update(userConnection));
    } else {
      this.subscribeToSaveResponse(this.userConnectionService.create(userConnection));
    }
  }

  private createFromForm(): IUserConnection {
    return {
      ...new UserConnection(),
      id: this.editForm.get(['id']).value,
      loginDate:
        this.editForm.get(['loginDate']).value != null ? moment(this.editForm.get(['loginDate']).value, DATE_TIME_FORMAT) : undefined,
      logoutDate:
        this.editForm.get(['logoutDate']).value != null ? moment(this.editForm.get(['logoutDate']).value, DATE_TIME_FORMAT) : undefined,
      userId: this.editForm.get(['userId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserConnection>>) {
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

  trackPgUserById(index: number, item: IPgUser) {
    return item.id;
  }
}
