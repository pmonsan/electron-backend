import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBeneficiary, Beneficiary } from 'app/shared/model/beneficiary.model';
import { BeneficiaryService } from './beneficiary.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer';

@Component({
  selector: 'jhi-beneficiary-update',
  templateUrl: './beneficiary-update.component.html'
})
export class BeneficiaryUpdateComponent implements OnInit {
  isSaving: boolean;

  customers: ICustomer[];

  editForm = this.fb.group({
    id: [],
    number: [null, [Validators.required, Validators.maxLength(10)]],
    isCompany: [null, [Validators.required]],
    firstname: [null, [Validators.maxLength(50)]],
    name: [null, [Validators.maxLength(50)]],
    aliasAccount: [null, [Validators.required, Validators.maxLength(50)]],
    baccBankCode: [null, [Validators.maxLength(10)]],
    baccBranchCode: [null, [Validators.maxLength(10)]],
    baccAccountNumber: [null, [Validators.maxLength(50)]],
    baccRibKey: [null, [Validators.maxLength(5)]],
    cardCvv2: [null, [Validators.maxLength(5)]],
    cardPan: [null, [Validators.maxLength(20)]],
    cardValidityDate: [null, [Validators.maxLength(8)]],
    isDmAccount: [null, [Validators.required]],
    momoAccountNumber: [null, [Validators.maxLength(20)]],
    beneficiaryRelationshipCode: [null, [Validators.required, Validators.maxLength(5)]],
    beneficiaryTypeCode: [null, [Validators.required, Validators.maxLength(5)]],
    active: [null, [Validators.required]],
    customerId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected beneficiaryService: BeneficiaryService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ beneficiary }) => {
      this.updateForm(beneficiary);
    });
    this.customerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICustomer[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICustomer[]>) => response.body)
      )
      .subscribe((res: ICustomer[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(beneficiary: IBeneficiary) {
    this.editForm.patchValue({
      id: beneficiary.id,
      number: beneficiary.number,
      isCompany: beneficiary.isCompany,
      firstname: beneficiary.firstname,
      name: beneficiary.name,
      aliasAccount: beneficiary.aliasAccount,
      baccBankCode: beneficiary.baccBankCode,
      baccBranchCode: beneficiary.baccBranchCode,
      baccAccountNumber: beneficiary.baccAccountNumber,
      baccRibKey: beneficiary.baccRibKey,
      cardCvv2: beneficiary.cardCvv2,
      cardPan: beneficiary.cardPan,
      cardValidityDate: beneficiary.cardValidityDate,
      isDmAccount: beneficiary.isDmAccount,
      momoAccountNumber: beneficiary.momoAccountNumber,
      beneficiaryRelationshipCode: beneficiary.beneficiaryRelationshipCode,
      beneficiaryTypeCode: beneficiary.beneficiaryTypeCode,
      active: beneficiary.active,
      customerId: beneficiary.customerId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const beneficiary = this.createFromForm();
    if (beneficiary.id !== undefined) {
      this.subscribeToSaveResponse(this.beneficiaryService.update(beneficiary));
    } else {
      this.subscribeToSaveResponse(this.beneficiaryService.create(beneficiary));
    }
  }

  private createFromForm(): IBeneficiary {
    return {
      ...new Beneficiary(),
      id: this.editForm.get(['id']).value,
      number: this.editForm.get(['number']).value,
      isCompany: this.editForm.get(['isCompany']).value,
      firstname: this.editForm.get(['firstname']).value,
      name: this.editForm.get(['name']).value,
      aliasAccount: this.editForm.get(['aliasAccount']).value,
      baccBankCode: this.editForm.get(['baccBankCode']).value,
      baccBranchCode: this.editForm.get(['baccBranchCode']).value,
      baccAccountNumber: this.editForm.get(['baccAccountNumber']).value,
      baccRibKey: this.editForm.get(['baccRibKey']).value,
      cardCvv2: this.editForm.get(['cardCvv2']).value,
      cardPan: this.editForm.get(['cardPan']).value,
      cardValidityDate: this.editForm.get(['cardValidityDate']).value,
      isDmAccount: this.editForm.get(['isDmAccount']).value,
      momoAccountNumber: this.editForm.get(['momoAccountNumber']).value,
      beneficiaryRelationshipCode: this.editForm.get(['beneficiaryRelationshipCode']).value,
      beneficiaryTypeCode: this.editForm.get(['beneficiaryTypeCode']).value,
      active: this.editForm.get(['active']).value,
      customerId: this.editForm.get(['customerId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBeneficiary>>) {
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
