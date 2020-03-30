import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IContract, Contract } from 'app/shared/model/contract.model';
import { ContractService } from './contract.service';
import { IPgAccount } from 'app/shared/model/pg-account.model';
import { PgAccountService } from 'app/entities/pg-account';

@Component({
  selector: 'jhi-contract-update',
  templateUrl: './contract-update.component.html'
})
export class ContractUpdateComponent implements OnInit {
  isSaving: boolean;

  pgaccounts: IPgAccount[];

  editForm = this.fb.group({
    id: [],
    number: [null, [Validators.maxLength(25)]],
    creationDate: [null, [Validators.required]],
    isMerchantContract: [null, [Validators.required]],
    modificationDate: [null, [Validators.required]],
    validationDate: [],
    filename: [null, [Validators.required, Validators.maxLength(100)]],
    customerCode: [null, [Validators.required, Validators.maxLength(5)]],
    active: [null, [Validators.required]],
    accountId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected contractService: ContractService,
    protected pgAccountService: PgAccountService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ contract }) => {
      this.updateForm(contract);
    });
    this.pgAccountService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPgAccount[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPgAccount[]>) => response.body)
      )
      .subscribe((res: IPgAccount[]) => (this.pgaccounts = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(contract: IContract) {
    this.editForm.patchValue({
      id: contract.id,
      number: contract.number,
      creationDate: contract.creationDate != null ? contract.creationDate.format(DATE_TIME_FORMAT) : null,
      isMerchantContract: contract.isMerchantContract,
      modificationDate: contract.modificationDate != null ? contract.modificationDate.format(DATE_TIME_FORMAT) : null,
      validationDate: contract.validationDate != null ? contract.validationDate.format(DATE_TIME_FORMAT) : null,
      filename: contract.filename,
      customerCode: contract.customerCode,
      active: contract.active,
      accountId: contract.accountId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const contract = this.createFromForm();
    if (contract.id !== undefined) {
      this.subscribeToSaveResponse(this.contractService.update(contract));
    } else {
      this.subscribeToSaveResponse(this.contractService.create(contract));
    }
  }

  private createFromForm(): IContract {
    return {
      ...new Contract(),
      id: this.editForm.get(['id']).value,
      number: this.editForm.get(['number']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      isMerchantContract: this.editForm.get(['isMerchantContract']).value,
      modificationDate:
        this.editForm.get(['modificationDate']).value != null
          ? moment(this.editForm.get(['modificationDate']).value, DATE_TIME_FORMAT)
          : undefined,
      validationDate:
        this.editForm.get(['validationDate']).value != null
          ? moment(this.editForm.get(['validationDate']).value, DATE_TIME_FORMAT)
          : undefined,
      filename: this.editForm.get(['filename']).value,
      customerCode: this.editForm.get(['customerCode']).value,
      active: this.editForm.get(['active']).value,
      accountId: this.editForm.get(['accountId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContract>>) {
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
