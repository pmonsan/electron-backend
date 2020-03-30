import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMeansofpayment, Meansofpayment } from 'app/shared/model/meansofpayment.model';
import { MeansofpaymentService } from './meansofpayment.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer';

@Component({
  selector: 'jhi-meansofpayment-update',
  templateUrl: './meansofpayment-update.component.html'
})
export class MeansofpaymentUpdateComponent implements OnInit {
  isSaving: boolean;

  customers: ICustomer[];

  editForm = this.fb.group({
    id: [],
    number: [null, [Validators.required, Validators.maxLength(10)]],
    aliasAccount: [null, [Validators.maxLength(50)]],
    baccBankCode: [null, [Validators.maxLength(10)]],
    baccBranchCode: [null, [Validators.maxLength(10)]],
    baccAccountNumber: [null, [Validators.maxLength(50)]],
    baccRibKey: [null, [Validators.maxLength(5)]],
    cardCvv2: [null, [Validators.maxLength(5)]],
    cardPan: [null, [Validators.maxLength(20)]],
    cardValidityDate: [null, [Validators.maxLength(8)]],
    momoAccount: [],
    meansofpaymentTypeCode: [null, [Validators.required, Validators.maxLength(5)]],
    issuerCode: [null, [Validators.required, Validators.maxLength(5)]],
    active: [null, [Validators.required]],
    customerId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected meansofpaymentService: MeansofpaymentService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ meansofpayment }) => {
      this.updateForm(meansofpayment);
    });
    this.customerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICustomer[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICustomer[]>) => response.body)
      )
      .subscribe((res: ICustomer[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(meansofpayment: IMeansofpayment) {
    this.editForm.patchValue({
      id: meansofpayment.id,
      number: meansofpayment.number,
      aliasAccount: meansofpayment.aliasAccount,
      baccBankCode: meansofpayment.baccBankCode,
      baccBranchCode: meansofpayment.baccBranchCode,
      baccAccountNumber: meansofpayment.baccAccountNumber,
      baccRibKey: meansofpayment.baccRibKey,
      cardCvv2: meansofpayment.cardCvv2,
      cardPan: meansofpayment.cardPan,
      cardValidityDate: meansofpayment.cardValidityDate,
      momoAccount: meansofpayment.momoAccount,
      meansofpaymentTypeCode: meansofpayment.meansofpaymentTypeCode,
      issuerCode: meansofpayment.issuerCode,
      active: meansofpayment.active,
      customerId: meansofpayment.customerId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const meansofpayment = this.createFromForm();
    if (meansofpayment.id !== undefined) {
      this.subscribeToSaveResponse(this.meansofpaymentService.update(meansofpayment));
    } else {
      this.subscribeToSaveResponse(this.meansofpaymentService.create(meansofpayment));
    }
  }

  private createFromForm(): IMeansofpayment {
    return {
      ...new Meansofpayment(),
      id: this.editForm.get(['id']).value,
      number: this.editForm.get(['number']).value,
      aliasAccount: this.editForm.get(['aliasAccount']).value,
      baccBankCode: this.editForm.get(['baccBankCode']).value,
      baccBranchCode: this.editForm.get(['baccBranchCode']).value,
      baccAccountNumber: this.editForm.get(['baccAccountNumber']).value,
      baccRibKey: this.editForm.get(['baccRibKey']).value,
      cardCvv2: this.editForm.get(['cardCvv2']).value,
      cardPan: this.editForm.get(['cardPan']).value,
      cardValidityDate: this.editForm.get(['cardValidityDate']).value,
      momoAccount: this.editForm.get(['momoAccount']).value,
      meansofpaymentTypeCode: this.editForm.get(['meansofpaymentTypeCode']).value,
      issuerCode: this.editForm.get(['issuerCode']).value,
      active: this.editForm.get(['active']).value,
      customerId: this.editForm.get(['customerId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMeansofpayment>>) {
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
