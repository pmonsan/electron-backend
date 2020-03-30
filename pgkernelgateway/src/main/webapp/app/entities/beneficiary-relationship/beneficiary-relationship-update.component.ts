import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IBeneficiaryRelationship, BeneficiaryRelationship } from 'app/shared/model/beneficiary-relationship.model';
import { BeneficiaryRelationshipService } from './beneficiary-relationship.service';

@Component({
  selector: 'jhi-beneficiary-relationship-update',
  templateUrl: './beneficiary-relationship-update.component.html'
})
export class BeneficiaryRelationshipUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    active: [null, [Validators.required]]
  });

  constructor(
    protected beneficiaryRelationshipService: BeneficiaryRelationshipService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ beneficiaryRelationship }) => {
      this.updateForm(beneficiaryRelationship);
    });
  }

  updateForm(beneficiaryRelationship: IBeneficiaryRelationship) {
    this.editForm.patchValue({
      id: beneficiaryRelationship.id,
      code: beneficiaryRelationship.code,
      label: beneficiaryRelationship.label,
      active: beneficiaryRelationship.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const beneficiaryRelationship = this.createFromForm();
    if (beneficiaryRelationship.id !== undefined) {
      this.subscribeToSaveResponse(this.beneficiaryRelationshipService.update(beneficiaryRelationship));
    } else {
      this.subscribeToSaveResponse(this.beneficiaryRelationshipService.create(beneficiaryRelationship));
    }
  }

  private createFromForm(): IBeneficiaryRelationship {
    return {
      ...new BeneficiaryRelationship(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBeneficiaryRelationship>>) {
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
