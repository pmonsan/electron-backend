import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ICustomerBlacklist, CustomerBlacklist } from 'app/shared/model/customer-blacklist.model';
import { CustomerBlacklistService } from './customer-blacklist.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer';

@Component({
  selector: 'jhi-customer-blacklist-update',
  templateUrl: './customer-blacklist-update.component.html'
})
export class CustomerBlacklistUpdateComponent implements OnInit {
  isSaving: boolean;

  customers: ICustomer[];

  editForm = this.fb.group({
    id: [],
    customerBlacklistStatus: [null, [Validators.required]],
    insertionDate: [null, [Validators.required]],
    modificationDate: [],
    comment: [null, [Validators.maxLength(255)]],
    active: [null, [Validators.required]],
    customerId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected customerBlacklistService: CustomerBlacklistService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ customerBlacklist }) => {
      this.updateForm(customerBlacklist);
    });
    this.customerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICustomer[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICustomer[]>) => response.body)
      )
      .subscribe((res: ICustomer[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(customerBlacklist: ICustomerBlacklist) {
    this.editForm.patchValue({
      id: customerBlacklist.id,
      customerBlacklistStatus: customerBlacklist.customerBlacklistStatus,
      insertionDate: customerBlacklist.insertionDate != null ? customerBlacklist.insertionDate.format(DATE_TIME_FORMAT) : null,
      modificationDate: customerBlacklist.modificationDate != null ? customerBlacklist.modificationDate.format(DATE_TIME_FORMAT) : null,
      comment: customerBlacklist.comment,
      active: customerBlacklist.active,
      customerId: customerBlacklist.customerId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const customerBlacklist = this.createFromForm();
    if (customerBlacklist.id !== undefined) {
      this.subscribeToSaveResponse(this.customerBlacklistService.update(customerBlacklist));
    } else {
      this.subscribeToSaveResponse(this.customerBlacklistService.create(customerBlacklist));
    }
  }

  private createFromForm(): ICustomerBlacklist {
    return {
      ...new CustomerBlacklist(),
      id: this.editForm.get(['id']).value,
      customerBlacklistStatus: this.editForm.get(['customerBlacklistStatus']).value,
      insertionDate:
        this.editForm.get(['insertionDate']).value != null
          ? moment(this.editForm.get(['insertionDate']).value, DATE_TIME_FORMAT)
          : undefined,
      modificationDate:
        this.editForm.get(['modificationDate']).value != null
          ? moment(this.editForm.get(['modificationDate']).value, DATE_TIME_FORMAT)
          : undefined,
      comment: this.editForm.get(['comment']).value,
      active: this.editForm.get(['active']).value,
      customerId: this.editForm.get(['customerId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerBlacklist>>) {
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
