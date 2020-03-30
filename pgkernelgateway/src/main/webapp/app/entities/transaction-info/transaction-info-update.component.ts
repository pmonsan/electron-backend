import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITransactionInfo, TransactionInfo } from 'app/shared/model/transaction-info.model';
import { TransactionInfoService } from './transaction-info.service';
import { ITransaction } from 'app/shared/model/transaction.model';
import { TransactionService } from 'app/entities/transaction';

@Component({
  selector: 'jhi-transaction-info-update',
  templateUrl: './transaction-info-update.component.html'
})
export class TransactionInfoUpdateComponent implements OnInit {
  isSaving: boolean;

  transactions: ITransaction[];

  editForm = this.fb.group({
    id: [],
    transactionPropertyCode: [null, [Validators.required, Validators.maxLength(5)]],
    value: [null, [Validators.required, Validators.maxLength(100)]],
    active: [null, [Validators.required]],
    transactionId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected transactionInfoService: TransactionInfoService,
    protected transactionService: TransactionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ transactionInfo }) => {
      this.updateForm(transactionInfo);
    });
    this.transactionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITransaction[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITransaction[]>) => response.body)
      )
      .subscribe((res: ITransaction[]) => (this.transactions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(transactionInfo: ITransactionInfo) {
    this.editForm.patchValue({
      id: transactionInfo.id,
      transactionPropertyCode: transactionInfo.transactionPropertyCode,
      value: transactionInfo.value,
      active: transactionInfo.active,
      transactionId: transactionInfo.transactionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const transactionInfo = this.createFromForm();
    if (transactionInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionInfoService.update(transactionInfo));
    } else {
      this.subscribeToSaveResponse(this.transactionInfoService.create(transactionInfo));
    }
  }

  private createFromForm(): ITransactionInfo {
    return {
      ...new TransactionInfo(),
      id: this.editForm.get(['id']).value,
      transactionPropertyCode: this.editForm.get(['transactionPropertyCode']).value,
      value: this.editForm.get(['value']).value,
      active: this.editForm.get(['active']).value,
      transactionId: this.editForm.get(['transactionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionInfo>>) {
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
