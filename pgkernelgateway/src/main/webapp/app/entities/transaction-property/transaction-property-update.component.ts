import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITransactionProperty, TransactionProperty } from 'app/shared/model/transaction-property.model';
import { TransactionPropertyService } from './transaction-property.service';

@Component({
  selector: 'jhi-transaction-property-update',
  templateUrl: './transaction-property-update.component.html'
})
export class TransactionPropertyUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    propertyType: [null, [Validators.required]],
    active: [null, [Validators.required]]
  });

  constructor(
    protected transactionPropertyService: TransactionPropertyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ transactionProperty }) => {
      this.updateForm(transactionProperty);
    });
  }

  updateForm(transactionProperty: ITransactionProperty) {
    this.editForm.patchValue({
      id: transactionProperty.id,
      code: transactionProperty.code,
      label: transactionProperty.label,
      propertyType: transactionProperty.propertyType,
      active: transactionProperty.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const transactionProperty = this.createFromForm();
    if (transactionProperty.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionPropertyService.update(transactionProperty));
    } else {
      this.subscribeToSaveResponse(this.transactionPropertyService.create(transactionProperty));
    }
  }

  private createFromForm(): ITransactionProperty {
    return {
      ...new TransactionProperty(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      propertyType: this.editForm.get(['propertyType']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionProperty>>) {
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
