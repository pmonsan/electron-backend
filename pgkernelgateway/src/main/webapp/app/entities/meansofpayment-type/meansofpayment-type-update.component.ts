import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IMeansofpaymentType, MeansofpaymentType } from 'app/shared/model/meansofpayment-type.model';
import { MeansofpaymentTypeService } from './meansofpayment-type.service';

@Component({
  selector: 'jhi-meansofpayment-type-update',
  templateUrl: './meansofpayment-type-update.component.html'
})
export class MeansofpaymentTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    active: [null, [Validators.required]]
  });

  constructor(
    protected meansofpaymentTypeService: MeansofpaymentTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ meansofpaymentType }) => {
      this.updateForm(meansofpaymentType);
    });
  }

  updateForm(meansofpaymentType: IMeansofpaymentType) {
    this.editForm.patchValue({
      id: meansofpaymentType.id,
      code: meansofpaymentType.code,
      label: meansofpaymentType.label,
      active: meansofpaymentType.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const meansofpaymentType = this.createFromForm();
    if (meansofpaymentType.id !== undefined) {
      this.subscribeToSaveResponse(this.meansofpaymentTypeService.update(meansofpaymentType));
    } else {
      this.subscribeToSaveResponse(this.meansofpaymentTypeService.create(meansofpaymentType));
    }
  }

  private createFromForm(): IMeansofpaymentType {
    return {
      ...new MeansofpaymentType(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMeansofpaymentType>>) {
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
