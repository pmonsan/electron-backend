import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDetailTransaction, DetailTransaction } from 'app/shared/model/detail-transaction.model';
import { DetailTransactionService } from './detail-transaction.service';
import { ITransaction } from 'app/shared/model/transaction.model';
import { TransactionService } from 'app/entities/transaction';

@Component({
  selector: 'jhi-detail-transaction-update',
  templateUrl: './detail-transaction-update.component.html'
})
export class DetailTransactionUpdateComponent implements OnInit {
  isSaving: boolean;

  transactions: ITransaction[];

  editForm = this.fb.group({
    id: [],
    pgDataCode: [null, [Validators.required, Validators.maxLength(5)]],
    dataValue: [],
    active: [null, [Validators.required]],
    transactionId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected detailTransactionService: DetailTransactionService,
    protected transactionService: TransactionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ detailTransaction }) => {
      this.updateForm(detailTransaction);
    });
    this.transactionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITransaction[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITransaction[]>) => response.body)
      )
      .subscribe((res: ITransaction[]) => (this.transactions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(detailTransaction: IDetailTransaction) {
    this.editForm.patchValue({
      id: detailTransaction.id,
      pgDataCode: detailTransaction.pgDataCode,
      dataValue: detailTransaction.dataValue,
      active: detailTransaction.active,
      transactionId: detailTransaction.transactionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const detailTransaction = this.createFromForm();
    if (detailTransaction.id !== undefined) {
      this.subscribeToSaveResponse(this.detailTransactionService.update(detailTransaction));
    } else {
      this.subscribeToSaveResponse(this.detailTransactionService.create(detailTransaction));
    }
  }

  private createFromForm(): IDetailTransaction {
    return {
      ...new DetailTransaction(),
      id: this.editForm.get(['id']).value,
      pgDataCode: this.editForm.get(['pgDataCode']).value,
      dataValue: this.editForm.get(['dataValue']).value,
      active: this.editForm.get(['active']).value,
      transactionId: this.editForm.get(['transactionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetailTransaction>>) {
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
