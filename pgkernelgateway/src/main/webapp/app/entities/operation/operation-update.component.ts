import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IOperation, Operation } from 'app/shared/model/operation.model';
import { OperationService } from './operation.service';
import { ITransaction } from 'app/shared/model/transaction.model';
import { TransactionService } from 'app/entities/transaction';

@Component({
  selector: 'jhi-operation-update',
  templateUrl: './operation-update.component.html'
})
export class OperationUpdateComponent implements OnInit {
  isSaving: boolean;

  transactions: ITransaction[];

  editForm = this.fb.group({
    id: [],
    number: [null, [Validators.required, Validators.maxLength(10)]],
    amount: [],
    direction: [null, [Validators.required, Validators.maxLength(1)]],
    operationDate: [null, [Validators.required]],
    accountNumber: [null, [Validators.required, Validators.maxLength(50)]],
    operationStatusCode: [null, [Validators.required, Validators.maxLength(5)]],
    operationTypeCode: [null, [Validators.required, Validators.maxLength(5)]],
    currencyCode: [null, [Validators.required, Validators.maxLength(5)]],
    userCode: [null, [Validators.required, Validators.maxLength(5)]],
    description: [null, [Validators.required, Validators.maxLength(255)]],
    active: [null, [Validators.required]],
    transactionId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected operationService: OperationService,
    protected transactionService: TransactionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ operation }) => {
      this.updateForm(operation);
    });
    this.transactionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITransaction[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITransaction[]>) => response.body)
      )
      .subscribe((res: ITransaction[]) => (this.transactions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(operation: IOperation) {
    this.editForm.patchValue({
      id: operation.id,
      number: operation.number,
      amount: operation.amount,
      direction: operation.direction,
      operationDate: operation.operationDate != null ? operation.operationDate.format(DATE_TIME_FORMAT) : null,
      accountNumber: operation.accountNumber,
      operationStatusCode: operation.operationStatusCode,
      operationTypeCode: operation.operationTypeCode,
      currencyCode: operation.currencyCode,
      userCode: operation.userCode,
      description: operation.description,
      active: operation.active,
      transactionId: operation.transactionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const operation = this.createFromForm();
    if (operation.id !== undefined) {
      this.subscribeToSaveResponse(this.operationService.update(operation));
    } else {
      this.subscribeToSaveResponse(this.operationService.create(operation));
    }
  }

  private createFromForm(): IOperation {
    return {
      ...new Operation(),
      id: this.editForm.get(['id']).value,
      number: this.editForm.get(['number']).value,
      amount: this.editForm.get(['amount']).value,
      direction: this.editForm.get(['direction']).value,
      operationDate:
        this.editForm.get(['operationDate']).value != null
          ? moment(this.editForm.get(['operationDate']).value, DATE_TIME_FORMAT)
          : undefined,
      accountNumber: this.editForm.get(['accountNumber']).value,
      operationStatusCode: this.editForm.get(['operationStatusCode']).value,
      operationTypeCode: this.editForm.get(['operationTypeCode']).value,
      currencyCode: this.editForm.get(['currencyCode']).value,
      userCode: this.editForm.get(['userCode']).value,
      description: this.editForm.get(['description']).value,
      active: this.editForm.get(['active']).value,
      transactionId: this.editForm.get(['transactionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOperation>>) {
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

  trackTransactionById(index: number, item: ITransaction) {
    return item.id;
  }
}
