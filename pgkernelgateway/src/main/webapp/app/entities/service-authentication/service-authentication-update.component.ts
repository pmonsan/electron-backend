import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IServiceAuthentication, ServiceAuthentication } from 'app/shared/model/service-authentication.model';
import { ServiceAuthenticationService } from './service-authentication.service';

@Component({
  selector: 'jhi-service-authentication-update',
  templateUrl: './service-authentication-update.component.html'
})
export class ServiceAuthenticationUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    active: [null, [Validators.required]]
  });

  constructor(
    protected serviceAuthenticationService: ServiceAuthenticationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceAuthentication }) => {
      this.updateForm(serviceAuthentication);
    });
  }

  updateForm(serviceAuthentication: IServiceAuthentication) {
    this.editForm.patchValue({
      id: serviceAuthentication.id,
      code: serviceAuthentication.code,
      label: serviceAuthentication.label,
      active: serviceAuthentication.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const serviceAuthentication = this.createFromForm();
    if (serviceAuthentication.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceAuthenticationService.update(serviceAuthentication));
    } else {
      this.subscribeToSaveResponse(this.serviceAuthenticationService.create(serviceAuthentication));
    }
  }

  private createFromForm(): IServiceAuthentication {
    return {
      ...new ServiceAuthentication(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceAuthentication>>) {
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
