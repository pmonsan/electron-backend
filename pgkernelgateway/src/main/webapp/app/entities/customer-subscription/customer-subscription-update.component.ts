import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ICustomerSubscription, CustomerSubscription } from 'app/shared/model/customer-subscription.model';
import { CustomerSubscriptionService } from './customer-subscription.service';

@Component({
  selector: 'jhi-customer-subscription-update',
  templateUrl: './customer-subscription-update.component.html'
})
export class CustomerSubscriptionUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    number: [null, [Validators.maxLength(25)]],
    creationDate: [null, [Validators.required]],
    isMerchantSubscription: [null, [Validators.required]],
    modificationDate: [null, [Validators.required]],
    validationDate: [],
    filename: [null, [Validators.required, Validators.maxLength(100)]],
    customerCode: [null, [Validators.required, Validators.maxLength(5)]],
    serviceCode: [null, [Validators.required, Validators.maxLength(5)]],
    accountNumber: [null, [Validators.maxLength(50)]],
    startDate: [null, [Validators.required]],
    endDate: [null, [Validators.required]],
    active: [null, [Validators.required]]
  });

  constructor(
    protected customerSubscriptionService: CustomerSubscriptionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ customerSubscription }) => {
      this.updateForm(customerSubscription);
    });
  }

  updateForm(customerSubscription: ICustomerSubscription) {
    this.editForm.patchValue({
      id: customerSubscription.id,
      number: customerSubscription.number,
      creationDate: customerSubscription.creationDate != null ? customerSubscription.creationDate.format(DATE_TIME_FORMAT) : null,
      isMerchantSubscription: customerSubscription.isMerchantSubscription,
      modificationDate:
        customerSubscription.modificationDate != null ? customerSubscription.modificationDate.format(DATE_TIME_FORMAT) : null,
      validationDate: customerSubscription.validationDate != null ? customerSubscription.validationDate.format(DATE_TIME_FORMAT) : null,
      filename: customerSubscription.filename,
      customerCode: customerSubscription.customerCode,
      serviceCode: customerSubscription.serviceCode,
      accountNumber: customerSubscription.accountNumber,
      startDate: customerSubscription.startDate != null ? customerSubscription.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: customerSubscription.endDate != null ? customerSubscription.endDate.format(DATE_TIME_FORMAT) : null,
      active: customerSubscription.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const customerSubscription = this.createFromForm();
    if (customerSubscription.id !== undefined) {
      this.subscribeToSaveResponse(this.customerSubscriptionService.update(customerSubscription));
    } else {
      this.subscribeToSaveResponse(this.customerSubscriptionService.create(customerSubscription));
    }
  }

  private createFromForm(): ICustomerSubscription {
    return {
      ...new CustomerSubscription(),
      id: this.editForm.get(['id']).value,
      number: this.editForm.get(['number']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      isMerchantSubscription: this.editForm.get(['isMerchantSubscription']).value,
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
      serviceCode: this.editForm.get(['serviceCode']).value,
      accountNumber: this.editForm.get(['accountNumber']).value,
      startDate:
        this.editForm.get(['startDate']).value != null ? moment(this.editForm.get(['startDate']).value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate']).value != null ? moment(this.editForm.get(['endDate']).value, DATE_TIME_FORMAT) : undefined,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerSubscription>>) {
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
