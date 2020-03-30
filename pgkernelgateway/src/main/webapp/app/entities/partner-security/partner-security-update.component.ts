import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPartnerSecurity, PartnerSecurity } from 'app/shared/model/partner-security.model';
import { PartnerSecurityService } from './partner-security.service';

@Component({
  selector: 'jhi-partner-security-update',
  templateUrl: './partner-security-update.component.html'
})
export class PartnerSecurityUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    active: [null, [Validators.required]]
  });

  constructor(
    protected partnerSecurityService: PartnerSecurityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ partnerSecurity }) => {
      this.updateForm(partnerSecurity);
    });
  }

  updateForm(partnerSecurity: IPartnerSecurity) {
    this.editForm.patchValue({
      id: partnerSecurity.id,
      code: partnerSecurity.code,
      label: partnerSecurity.label,
      active: partnerSecurity.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const partnerSecurity = this.createFromForm();
    if (partnerSecurity.id !== undefined) {
      this.subscribeToSaveResponse(this.partnerSecurityService.update(partnerSecurity));
    } else {
      this.subscribeToSaveResponse(this.partnerSecurityService.create(partnerSecurity));
    }
  }

  private createFromForm(): IPartnerSecurity {
    return {
      ...new PartnerSecurity(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartnerSecurity>>) {
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
