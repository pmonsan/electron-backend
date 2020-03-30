import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IServiceIntegration, ServiceIntegration } from 'app/shared/model/service-integration.model';
import { ServiceIntegrationService } from './service-integration.service';
import { IPartner } from 'app/shared/model/partner.model';
import { PartnerService } from 'app/entities/partner';

@Component({
  selector: 'jhi-service-integration-update',
  templateUrl: './service-integration-update.component.html'
})
export class ServiceIntegrationUpdateComponent implements OnInit {
  isSaving: boolean;

  partners: IPartner[];

  editForm = this.fb.group({
    id: [],
    customerRef: [null, [Validators.required, Validators.maxLength(30)]],
    serviceCode: [null, [Validators.maxLength(5)]],
    active: [null, [Validators.required]],
    partnerId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected serviceIntegrationService: ServiceIntegrationService,
    protected partnerService: PartnerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceIntegration }) => {
      this.updateForm(serviceIntegration);
    });
    this.partnerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPartner[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPartner[]>) => response.body)
      )
      .subscribe((res: IPartner[]) => (this.partners = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(serviceIntegration: IServiceIntegration) {
    this.editForm.patchValue({
      id: serviceIntegration.id,
      customerRef: serviceIntegration.customerRef,
      serviceCode: serviceIntegration.serviceCode,
      active: serviceIntegration.active,
      partnerId: serviceIntegration.partnerId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const serviceIntegration = this.createFromForm();
    if (serviceIntegration.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceIntegrationService.update(serviceIntegration));
    } else {
      this.subscribeToSaveResponse(this.serviceIntegrationService.create(serviceIntegration));
    }
  }

  private createFromForm(): IServiceIntegration {
    return {
      ...new ServiceIntegration(),
      id: this.editForm.get(['id']).value,
      customerRef: this.editForm.get(['customerRef']).value,
      serviceCode: this.editForm.get(['serviceCode']).value,
      active: this.editForm.get(['active']).value,
      partnerId: this.editForm.get(['partnerId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceIntegration>>) {
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

  trackPartnerById(index: number, item: IPartner) {
    return item.id;
  }
}
