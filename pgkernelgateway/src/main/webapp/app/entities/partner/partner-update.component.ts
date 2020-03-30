import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPartner, Partner } from 'app/shared/model/partner.model';
import { PartnerService } from './partner.service';
import { IPartnerSecurity } from 'app/shared/model/partner-security.model';
import { PartnerSecurityService } from 'app/entities/partner-security';

@Component({
  selector: 'jhi-partner-update',
  templateUrl: './partner-update.component.html'
})
export class PartnerUpdateComponent implements OnInit {
  isSaving: boolean;

  partnersecurities: IPartnerSecurity[];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    name: [null, [Validators.required, Validators.maxLength(50)]],
    apiKey: [null, [Validators.required, Validators.maxLength(100)]],
    address: [null, [Validators.maxLength(255)]],
    city: [null, [Validators.maxLength(100)]],
    postalCode: [null, [Validators.maxLength(10)]],
    countryCode: [null, [Validators.maxLength(5)]],
    rsaPublicKeyPath: [null, [Validators.maxLength(255)]],
    contactFirstname: [null, [Validators.required, Validators.maxLength(50)]],
    contactLastname: [null, [Validators.required, Validators.maxLength(50)]],
    businessEmail: [null, [Validators.required, Validators.maxLength(100), Validators.pattern('^[^@s]+@[^@s]+.[^@s]+$')]],
    businessPhone: [null, [Validators.required, Validators.maxLength(20)]],
    partnerTypeCode: [null, [Validators.maxLength(5)]],
    active: [null, [Validators.required]],
    partnerSecurityId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected partnerService: PartnerService,
    protected partnerSecurityService: PartnerSecurityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ partner }) => {
      this.updateForm(partner);
    });
    this.partnerSecurityService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPartnerSecurity[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPartnerSecurity[]>) => response.body)
      )
      .subscribe((res: IPartnerSecurity[]) => (this.partnersecurities = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(partner: IPartner) {
    this.editForm.patchValue({
      id: partner.id,
      code: partner.code,
      name: partner.name,
      apiKey: partner.apiKey,
      address: partner.address,
      city: partner.city,
      postalCode: partner.postalCode,
      countryCode: partner.countryCode,
      rsaPublicKeyPath: partner.rsaPublicKeyPath,
      contactFirstname: partner.contactFirstname,
      contactLastname: partner.contactLastname,
      businessEmail: partner.businessEmail,
      businessPhone: partner.businessPhone,
      partnerTypeCode: partner.partnerTypeCode,
      active: partner.active,
      partnerSecurityId: partner.partnerSecurityId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const partner = this.createFromForm();
    if (partner.id !== undefined) {
      this.subscribeToSaveResponse(this.partnerService.update(partner));
    } else {
      this.subscribeToSaveResponse(this.partnerService.create(partner));
    }
  }

  private createFromForm(): IPartner {
    return {
      ...new Partner(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      apiKey: this.editForm.get(['apiKey']).value,
      address: this.editForm.get(['address']).value,
      city: this.editForm.get(['city']).value,
      postalCode: this.editForm.get(['postalCode']).value,
      countryCode: this.editForm.get(['countryCode']).value,
      rsaPublicKeyPath: this.editForm.get(['rsaPublicKeyPath']).value,
      contactFirstname: this.editForm.get(['contactFirstname']).value,
      contactLastname: this.editForm.get(['contactLastname']).value,
      businessEmail: this.editForm.get(['businessEmail']).value,
      businessPhone: this.editForm.get(['businessPhone']).value,
      partnerTypeCode: this.editForm.get(['partnerTypeCode']).value,
      active: this.editForm.get(['active']).value,
      partnerSecurityId: this.editForm.get(['partnerSecurityId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartner>>) {
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

  trackPartnerSecurityById(index: number, item: IPartnerSecurity) {
    return item.id;
  }
}
