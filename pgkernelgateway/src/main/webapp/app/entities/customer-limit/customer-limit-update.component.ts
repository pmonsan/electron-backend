import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICustomerLimit, CustomerLimit } from 'app/shared/model/customer-limit.model';
import { CustomerLimitService } from './customer-limit.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer';

@Component({
  selector: 'jhi-customer-limit-update',
  templateUrl: './customer-limit-update.component.html'
})
export class CustomerLimitUpdateComponent implements OnInit {
  isSaving: boolean;

  customers: ICustomer[];

  editForm = this.fb.group({
    id: [],
    limitTypeCode: [null, [Validators.required, Validators.maxLength(5)]],
    accountTypeCode: [null, [Validators.required, Validators.maxLength(5)]],
    customerTypeCode: [null, [Validators.required, Validators.maxLength(5)]],
    value: [],
    comment: [null, [Validators.maxLength(255)]],
    active: [null, [Validators.required]],
    customerId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected customerLimitService: CustomerLimitService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ customerLimit }) => {
      this.updateForm(customerLimit);
    });
    this.customerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICustomer[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICustomer[]>) => response.body)
      )
      .subscribe((res: ICustomer[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(customerLimit: ICustomerLimit) {
    this.editForm.patchValue({
      id: customerLimit.id,
      limitTypeCode: customerLimit.limitTypeCode,
      accountTypeCode: customerLimit.accountTypeCode,
      customerTypeCode: customerLimit.customerTypeCode,
      value: customerLimit.value,
      comment: customerLimit.comment,
      active: customerLimit.active,
      customerId: customerLimit.customerId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const customerLimit = this.createFromForm();
    if (customerLimit.id !== undefined) {
      this.subscribeToSaveResponse(this.customerLimitService.update(customerLimit));
    } else {
      this.subscribeToSaveResponse(this.customerLimitService.create(customerLimit));
    }
  }

  private createFromForm(): ICustomerLimit {
    return {
      ...new CustomerLimit(),
      id: this.editForm.get(['id']).value,
      limitTypeCode: this.editForm.get(['limitTypeCode']).value,
      accountTypeCode: this.editForm.get(['accountTypeCode']).value,
      customerTypeCode: this.editForm.get(['customerTypeCode']).value,
      value: this.editForm.get(['value']).value,
      comment: this.editForm.get(['comment']).value,
      active: this.editForm.get(['active']).value,
      customerId: this.editForm.get(['customerId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerLimit>>) {
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

  trackCustomerById(index: number, item: ICustomer) {
    return item.id;
  }
}
