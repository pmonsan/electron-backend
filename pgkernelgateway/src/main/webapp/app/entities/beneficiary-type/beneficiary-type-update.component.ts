import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IBeneficiaryType, BeneficiaryType } from 'app/shared/model/beneficiary-type.model';
import { BeneficiaryTypeService } from './beneficiary-type.service';

@Component({
  selector: 'jhi-beneficiary-type-update',
  templateUrl: './beneficiary-type-update.component.html'
})
export class BeneficiaryTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    active: [null, [Validators.required]]
  });

  constructor(
    protected beneficiaryTypeService: BeneficiaryTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ beneficiaryType }) => {
      this.updateForm(beneficiaryType);
    });
  }

  updateForm(beneficiaryType: IBeneficiaryType) {
    this.editForm.patchValue({
      id: beneficiaryType.id,
      code: beneficiaryType.code,
      label: beneficiaryType.label,
      active: beneficiaryType.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const beneficiaryType = this.createFromForm();
    if (beneficiaryType.id !== undefined) {
      this.subscribeToSaveResponse(this.beneficiaryTypeService.update(beneficiaryType));
    } else {
      this.subscribeToSaveResponse(this.beneficiaryTypeService.create(beneficiaryType));
    }
  }

  private createFromForm(): IBeneficiaryType {
    return {
      ...new BeneficiaryType(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBeneficiaryType>>) {
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
