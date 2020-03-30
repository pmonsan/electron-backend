import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IPgAccount, PgAccount } from 'app/shared/model/pg-account.model';
import { PgAccountService } from './pg-account.service';

@Component({
  selector: 'jhi-pg-account-update',
  templateUrl: './pg-account-update.component.html'
})
export class PgAccountUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    number: [null, [Validators.required, Validators.maxLength(50)]],
    openingDate: [null, [Validators.required]],
    temporary: [null, [Validators.required]],
    closingDate: [],
    imsi: [null, [Validators.maxLength(20)]],
    transactionCode: [null, [Validators.required, Validators.pattern('^[A-Za-z0-9]{4}$')]],
    validationDate: [],
    accountStatusCode: [null, [Validators.required, Validators.maxLength(5)]],
    accountTypeCode: [null, [Validators.required, Validators.maxLength(5)]],
    customerCode: [null, [Validators.required, Validators.maxLength(5)]],
    currencyCode: [null, [Validators.required, Validators.maxLength(5)]],
    comment: [null, [Validators.maxLength(255)]],
    active: [null, [Validators.required]]
  });

  constructor(protected pgAccountService: PgAccountService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pgAccount }) => {
      this.updateForm(pgAccount);
    });
  }

  updateForm(pgAccount: IPgAccount) {
    this.editForm.patchValue({
      id: pgAccount.id,
      number: pgAccount.number,
      openingDate: pgAccount.openingDate != null ? pgAccount.openingDate.format(DATE_TIME_FORMAT) : null,
      temporary: pgAccount.temporary,
      closingDate: pgAccount.closingDate != null ? pgAccount.closingDate.format(DATE_TIME_FORMAT) : null,
      imsi: pgAccount.imsi,
      transactionCode: pgAccount.transactionCode,
      validationDate: pgAccount.validationDate != null ? pgAccount.validationDate.format(DATE_TIME_FORMAT) : null,
      accountStatusCode: pgAccount.accountStatusCode,
      accountTypeCode: pgAccount.accountTypeCode,
      customerCode: pgAccount.customerCode,
      currencyCode: pgAccount.currencyCode,
      comment: pgAccount.comment,
      active: pgAccount.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pgAccount = this.createFromForm();
    if (pgAccount.id !== undefined) {
      this.subscribeToSaveResponse(this.pgAccountService.update(pgAccount));
    } else {
      this.subscribeToSaveResponse(this.pgAccountService.create(pgAccount));
    }
  }

  private createFromForm(): IPgAccount {
    return {
      ...new PgAccount(),
      id: this.editForm.get(['id']).value,
      number: this.editForm.get(['number']).value,
      openingDate:
        this.editForm.get(['openingDate']).value != null ? moment(this.editForm.get(['openingDate']).value, DATE_TIME_FORMAT) : undefined,
      temporary: this.editForm.get(['temporary']).value,
      closingDate:
        this.editForm.get(['closingDate']).value != null ? moment(this.editForm.get(['closingDate']).value, DATE_TIME_FORMAT) : undefined,
      imsi: this.editForm.get(['imsi']).value,
      transactionCode: this.editForm.get(['transactionCode']).value,
      validationDate:
        this.editForm.get(['validationDate']).value != null
          ? moment(this.editForm.get(['validationDate']).value, DATE_TIME_FORMAT)
          : undefined,
      accountStatusCode: this.editForm.get(['accountStatusCode']).value,
      accountTypeCode: this.editForm.get(['accountTypeCode']).value,
      customerCode: this.editForm.get(['customerCode']).value,
      currencyCode: this.editForm.get(['currencyCode']).value,
      comment: this.editForm.get(['comment']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPgAccount>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
