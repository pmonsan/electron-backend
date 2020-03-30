import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IInternalConnectorRequest, InternalConnectorRequest } from 'app/shared/model/internal-connector-request.model';
import { InternalConnectorRequestService } from './internal-connector-request.service';

@Component({
  selector: 'jhi-internal-connector-request-update',
  templateUrl: './internal-connector-request-update.component.html'
})
export class InternalConnectorRequestUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    number: [null, [Validators.required, Validators.maxLength(50)]],
    module: [null, [Validators.required, Validators.maxLength(10)]],
    connector: [null, [Validators.required, Validators.maxLength(20)]],
    connectorType: [null, [Validators.required, Validators.maxLength(20)]],
    requestData: [null, [Validators.maxLength(255)]],
    registrationDate: [null, [Validators.required]],
    pgapsTransactionNumber: [null, [Validators.maxLength(50)]],
    serviceId: [null, [Validators.maxLength(50)]],
    accountNumber: [null, [Validators.maxLength(50)]],
    amount: [null, [Validators.min(0)]],
    balance: [null, [Validators.min(0)]],
    accountValidation: [],
    numberOfTransactions: [],
    lastTransactions: [],
    action: [null, [Validators.maxLength(50)]],
    responseDate: [],
    status: [null, [Validators.maxLength(100)]],
    reason: [null, [Validators.maxLength(100)]],
    partnerTransactionNumber: [null, [Validators.maxLength(50)]],
    active: [null, [Validators.required]]
  });

  constructor(
    protected internalConnectorRequestService: InternalConnectorRequestService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ internalConnectorRequest }) => {
      this.updateForm(internalConnectorRequest);
    });
  }

  updateForm(internalConnectorRequest: IInternalConnectorRequest) {
    this.editForm.patchValue({
      id: internalConnectorRequest.id,
      number: internalConnectorRequest.number,
      module: internalConnectorRequest.module,
      connector: internalConnectorRequest.connector,
      connectorType: internalConnectorRequest.connectorType,
      requestData: internalConnectorRequest.requestData,
      registrationDate:
        internalConnectorRequest.registrationDate != null ? internalConnectorRequest.registrationDate.format(DATE_TIME_FORMAT) : null,
      pgapsTransactionNumber: internalConnectorRequest.pgapsTransactionNumber,
      serviceId: internalConnectorRequest.serviceId,
      accountNumber: internalConnectorRequest.accountNumber,
      amount: internalConnectorRequest.amount,
      balance: internalConnectorRequest.balance,
      accountValidation: internalConnectorRequest.accountValidation,
      numberOfTransactions: internalConnectorRequest.numberOfTransactions,
      lastTransactions: internalConnectorRequest.lastTransactions,
      action: internalConnectorRequest.action,
      responseDate: internalConnectorRequest.responseDate != null ? internalConnectorRequest.responseDate.format(DATE_TIME_FORMAT) : null,
      status: internalConnectorRequest.status,
      reason: internalConnectorRequest.reason,
      partnerTransactionNumber: internalConnectorRequest.partnerTransactionNumber,
      active: internalConnectorRequest.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const internalConnectorRequest = this.createFromForm();
    if (internalConnectorRequest.id !== undefined) {
      this.subscribeToSaveResponse(this.internalConnectorRequestService.update(internalConnectorRequest));
    } else {
      this.subscribeToSaveResponse(this.internalConnectorRequestService.create(internalConnectorRequest));
    }
  }

  private createFromForm(): IInternalConnectorRequest {
    return {
      ...new InternalConnectorRequest(),
      id: this.editForm.get(['id']).value,
      number: this.editForm.get(['number']).value,
      module: this.editForm.get(['module']).value,
      connector: this.editForm.get(['connector']).value,
      connectorType: this.editForm.get(['connectorType']).value,
      requestData: this.editForm.get(['requestData']).value,
      registrationDate:
        this.editForm.get(['registrationDate']).value != null
          ? moment(this.editForm.get(['registrationDate']).value, DATE_TIME_FORMAT)
          : undefined,
      pgapsTransactionNumber: this.editForm.get(['pgapsTransactionNumber']).value,
      serviceId: this.editForm.get(['serviceId']).value,
      accountNumber: this.editForm.get(['accountNumber']).value,
      amount: this.editForm.get(['amount']).value,
      balance: this.editForm.get(['balance']).value,
      accountValidation: this.editForm.get(['accountValidation']).value,
      numberOfTransactions: this.editForm.get(['numberOfTransactions']).value,
      lastTransactions: this.editForm.get(['lastTransactions']).value,
      action: this.editForm.get(['action']).value,
      responseDate:
        this.editForm.get(['responseDate']).value != null ? moment(this.editForm.get(['responseDate']).value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status']).value,
      reason: this.editForm.get(['reason']).value,
      partnerTransactionNumber: this.editForm.get(['partnerTransactionNumber']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInternalConnectorRequest>>) {
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
