import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPartnerType, PartnerType } from 'app/shared/model/partner-type.model';
import { PartnerTypeService } from './partner-type.service';

@Component({
  selector: 'jhi-partner-type-update',
  templateUrl: './partner-type-update.component.html'
})
export class PartnerTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    description: [null, [Validators.maxLength(255)]],
    active: [null, [Validators.required]]
  });

  constructor(protected partnerTypeService: PartnerTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ partnerType }) => {
      this.updateForm(partnerType);
    });
  }

  updateForm(partnerType: IPartnerType) {
    this.editForm.patchValue({
      id: partnerType.id,
      code: partnerType.code,
      label: partnerType.label,
      description: partnerType.description,
      active: partnerType.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const partnerType = this.createFromForm();
    if (partnerType.id !== undefined) {
      this.subscribeToSaveResponse(this.partnerTypeService.update(partnerType));
    } else {
      this.subscribeToSaveResponse(this.partnerTypeService.create(partnerType));
    }
  }

  private createFromForm(): IPartnerType {
    return {
      ...new PartnerType(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      description: this.editForm.get(['description']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartnerType>>) {
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
