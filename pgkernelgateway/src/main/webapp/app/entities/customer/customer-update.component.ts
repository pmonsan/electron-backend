import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICustomer, Customer } from 'app/shared/model/customer.model';
import { CustomerService } from './customer.service';

@Component({
  selector: 'jhi-customer-update',
  templateUrl: './customer-update.component.html'
})
export class CustomerUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    number: [null, [Validators.maxLength(25)]],
    corporateName: [null, [Validators.maxLength(50)]],
    firstname: [null, [Validators.maxLength(50)]],
    lastname: [null, [Validators.maxLength(50)]],
    gpsLatitude: [null, [Validators.min(0)]],
    gpsLongitude: [null, [Validators.min(0)]],
    homePhone: [null, [Validators.maxLength(20)]],
    mobilePhone: [null, [Validators.maxLength(20)]],
    workPhone: [null, [Validators.maxLength(20)]],
    otherQuestion: [null, [Validators.maxLength(150)]],
    responseOfQuestion: [null, [Validators.maxLength(150)]],
    tradeRegister: [null, [Validators.maxLength(50)]],
    address: [null, [Validators.maxLength(255)]],
    postalCode: [null, [Validators.maxLength(10)]],
    city: [null, [Validators.maxLength(50)]],
    email: [null, [Validators.pattern('^[^@s]+@[^@s]+.[^@s]+$')]],
    countryCode: [null, [Validators.required, Validators.maxLength(5)]],
    partnerCode: [null, [Validators.required, Validators.maxLength(10)]],
    activityAreaCode: [null, [Validators.required, Validators.maxLength(5)]],
    customerTypeCode: [null, [Validators.required, Validators.maxLength(5)]],
    questionCode: [null, [Validators.required, Validators.maxLength(5)]],
    username: [null, [Validators.maxLength(50)]],
    active: [null, [Validators.required]]
  });

  constructor(protected customerService: CustomerService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ customer }) => {
      this.updateForm(customer);
    });
  }

  updateForm(customer: ICustomer) {
    this.editForm.patchValue({
      id: customer.id,
      number: customer.number,
      corporateName: customer.corporateName,
      firstname: customer.firstname,
      lastname: customer.lastname,
      gpsLatitude: customer.gpsLatitude,
      gpsLongitude: customer.gpsLongitude,
      homePhone: customer.homePhone,
      mobilePhone: customer.mobilePhone,
      workPhone: customer.workPhone,
      otherQuestion: customer.otherQuestion,
      responseOfQuestion: customer.responseOfQuestion,
      tradeRegister: customer.tradeRegister,
      address: customer.address,
      postalCode: customer.postalCode,
      city: customer.city,
      email: customer.email,
      countryCode: customer.countryCode,
      partnerCode: customer.partnerCode,
      activityAreaCode: customer.activityAreaCode,
      customerTypeCode: customer.customerTypeCode,
      questionCode: customer.questionCode,
      username: customer.username,
      active: customer.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const customer = this.createFromForm();
    if (customer.id !== undefined) {
      this.subscribeToSaveResponse(this.customerService.update(customer));
    } else {
      this.subscribeToSaveResponse(this.customerService.create(customer));
    }
  }

  private createFromForm(): ICustomer {
    return {
      ...new Customer(),
      id: this.editForm.get(['id']).value,
      number: this.editForm.get(['number']).value,
      corporateName: this.editForm.get(['corporateName']).value,
      firstname: this.editForm.get(['firstname']).value,
      lastname: this.editForm.get(['lastname']).value,
      gpsLatitude: this.editForm.get(['gpsLatitude']).value,
      gpsLongitude: this.editForm.get(['gpsLongitude']).value,
      homePhone: this.editForm.get(['homePhone']).value,
      mobilePhone: this.editForm.get(['mobilePhone']).value,
      workPhone: this.editForm.get(['workPhone']).value,
      otherQuestion: this.editForm.get(['otherQuestion']).value,
      responseOfQuestion: this.editForm.get(['responseOfQuestion']).value,
      tradeRegister: this.editForm.get(['tradeRegister']).value,
      address: this.editForm.get(['address']).value,
      postalCode: this.editForm.get(['postalCode']).value,
      city: this.editForm.get(['city']).value,
      email: this.editForm.get(['email']).value,
      countryCode: this.editForm.get(['countryCode']).value,
      partnerCode: this.editForm.get(['partnerCode']).value,
      activityAreaCode: this.editForm.get(['activityAreaCode']).value,
      customerTypeCode: this.editForm.get(['customerTypeCode']).value,
      questionCode: this.editForm.get(['questionCode']).value,
      username: this.editForm.get(['username']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomer>>) {
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
