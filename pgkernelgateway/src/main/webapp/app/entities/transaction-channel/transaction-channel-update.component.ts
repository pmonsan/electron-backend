import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITransactionChannel, TransactionChannel } from 'app/shared/model/transaction-channel.model';
import { TransactionChannelService } from './transaction-channel.service';

@Component({
  selector: 'jhi-transaction-channel-update',
  templateUrl: './transaction-channel-update.component.html'
})
export class TransactionChannelUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    active: [null, [Validators.required]]
  });

  constructor(
    protected transactionChannelService: TransactionChannelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ transactionChannel }) => {
      this.updateForm(transactionChannel);
    });
  }

  updateForm(transactionChannel: ITransactionChannel) {
    this.editForm.patchValue({
      id: transactionChannel.id,
      code: transactionChannel.code,
      label: transactionChannel.label,
      active: transactionChannel.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const transactionChannel = this.createFromForm();
    if (transactionChannel.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionChannelService.update(transactionChannel));
    } else {
      this.subscribeToSaveResponse(this.transactionChannelService.create(transactionChannel));
    }
  }

  private createFromForm(): ITransactionChannel {
    return {
      ...new TransactionChannel(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionChannel>>) {
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
