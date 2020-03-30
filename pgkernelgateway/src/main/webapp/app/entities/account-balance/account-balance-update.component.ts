import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAccountBalance, AccountBalance } from 'app/shared/model/account-balance.model';
import { AccountBalanceService } from './account-balance.service';
import { IPgAccount } from 'app/shared/model/pg-account.model';
import { PgAccountService } from 'app/entities/pg-account';

@Component({
  selector: 'jhi-account-balance-update',
  templateUrl: './account-balance-update.component.html'
})
export class AccountBalanceUpdateComponent implements OnInit {
  isSaving: boolean;

  pgaccounts: IPgAccount[];

  editForm = this.fb.group({
    id: [],
    situationDate: [null, [Validators.required]],
    balance: [null, [Validators.min(0)]],
    active: [null, [Validators.required]],
    accountId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected accountBalanceService: AccountBalanceService,
    protected pgAccountService: PgAccountService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ accountBalance }) => {
      this.updateForm(accountBalance);
    });
    this.pgAccountService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPgAccount[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPgAccount[]>) => response.body)
      )
      .subscribe((res: IPgAccount[]) => (this.pgaccounts = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(accountBalance: IAccountBalance) {
    this.editForm.patchValue({
      id: accountBalance.id,
      situationDate: accountBalance.situationDate != null ? accountBalance.situationDate.format(DATE_TIME_FORMAT) : null,
      balance: accountBalance.balance,
      active: accountBalance.active,
      accountId: accountBalance.accountId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const accountBalance = this.createFromForm();
    if (accountBalance.id !== undefined) {
      this.subscribeToSaveResponse(this.accountBalanceService.update(accountBalance));
    } else {
      this.subscribeToSaveResponse(this.accountBalanceService.create(accountBalance));
    }
  }

  private createFromForm(): IAccountBalance {
    return {
      ...new AccountBalance(),
      id: this.editForm.get(['id']).value,
      situationDate:
        this.editForm.get(['situationDate']).value != null
          ? moment(this.editForm.get(['situationDate']).value, DATE_TIME_FORMAT)
          : undefined,
      balance: this.editForm.get(['balance']).value,
      active: this.editForm.get(['active']).value,
      accountId: this.editForm.get(['accountId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccountBalance>>) {
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
