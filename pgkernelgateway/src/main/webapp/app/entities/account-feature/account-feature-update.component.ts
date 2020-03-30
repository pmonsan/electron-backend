import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAccountFeature, AccountFeature } from 'app/shared/model/account-feature.model';
import { AccountFeatureService } from './account-feature.service';
import { IPgAccount } from 'app/shared/model/pg-account.model';
import { PgAccountService } from 'app/entities/pg-account';

@Component({
  selector: 'jhi-account-feature-update',
  templateUrl: './account-feature-update.component.html'
})
export class AccountFeatureUpdateComponent implements OnInit {
  isSaving: boolean;

  pgaccounts: IPgAccount[];

  editForm = this.fb.group({
    id: [],
    activationDate: [null, [Validators.required]],
    featureCode: [null, [Validators.required, Validators.maxLength(5)]],
    active: [null, [Validators.required]],
    accountId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected accountFeatureService: AccountFeatureService,
    protected pgAccountService: PgAccountService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ accountFeature }) => {
      this.updateForm(accountFeature);
    });
    this.pgAccountService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPgAccount[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPgAccount[]>) => response.body)
      )
      .subscribe((res: IPgAccount[]) => (this.pgaccounts = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(accountFeature: IAccountFeature) {
    this.editForm.patchValue({
      id: accountFeature.id,
      activationDate: accountFeature.activationDate != null ? accountFeature.activationDate.format(DATE_TIME_FORMAT) : null,
      featureCode: accountFeature.featureCode,
      active: accountFeature.active,
      accountId: accountFeature.accountId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const accountFeature = this.createFromForm();
    if (accountFeature.id !== undefined) {
      this.subscribeToSaveResponse(this.accountFeatureService.update(accountFeature));
    } else {
      this.subscribeToSaveResponse(this.accountFeatureService.create(accountFeature));
    }
  }

  private createFromForm(): IAccountFeature {
    return {
      ...new AccountFeature(),
      id: this.editForm.get(['id']).value,
      activationDate:
        this.editForm.get(['activationDate']).value != null
          ? moment(this.editForm.get(['activationDate']).value, DATE_TIME_FORMAT)
          : undefined,
      featureCode: this.editForm.get(['featureCode']).value,
      active: this.editForm.get(['active']).value,
      accountId: this.editForm.get(['accountId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccountFeature>>) {
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

  trackPgAccountById(index: number, item: IPgAccount) {
    return item.id;
  }
}
