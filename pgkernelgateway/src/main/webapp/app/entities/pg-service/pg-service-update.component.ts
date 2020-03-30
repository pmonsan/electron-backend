import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPgService, PgService } from 'app/shared/model/pg-service.model';
import { PgServiceService } from './pg-service.service';
import { IConnector } from 'app/shared/model/connector.model';
import { ConnectorService } from 'app/entities/connector';

@Component({
  selector: 'jhi-pg-service-update',
  templateUrl: './pg-service-update.component.html'
})
export class PgServiceUpdateComponent implements OnInit {
  isSaving: boolean;

  connectors: IConnector[];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    name: [null, [Validators.required, Validators.maxLength(100)]],
    isNative: [null, [Validators.required]],
    isSourceInternal: [null, [Validators.required]],
    isDestinationInternal: [null, [Validators.required]],
    needSubscription: [null, [Validators.required]],
    currencyCode: [null, [Validators.required, Validators.maxLength(5)]],
    useTransactionType: [null, [Validators.required]],
    checkSubscription: [null, [Validators.required]],
    ignoreFees: [null, [Validators.required]],
    ignoreLimit: [null, [Validators.required]],
    ignoreCommission: [null, [Validators.required]],
    checkOtp: [null, [Validators.required]],
    pgTransactionType1Code: [null, [Validators.required, Validators.maxLength(5)]],
    pgTransactionType2Code: [null, [Validators.required, Validators.maxLength(5)]],
    partnerOwnerCode: [null, [Validators.maxLength(5)]],
    transactionTypeCode: [null, [Validators.required, Validators.maxLength(5)]],
    serviceAuthenticationCode: [null, [Validators.required, Validators.maxLength(5)]],
    contractPath: [null, [Validators.maxLength(255)]],
    description: [null, [Validators.maxLength(255)]],
    logic: [],
    active: [null, [Validators.required]],
    sourceConnectorId: [],
    destinationConnectorId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected pgServiceService: PgServiceService,
    protected connectorService: ConnectorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pgService }) => {
      this.updateForm(pgService);
    });
    this.connectorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IConnector[]>) => mayBeOk.ok),
        map((response: HttpResponse<IConnector[]>) => response.body)
      )
      .subscribe((res: IConnector[]) => (this.connectors = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(pgService: IPgService) {
    this.editForm.patchValue({
      id: pgService.id,
      code: pgService.code,
      name: pgService.name,
      isNative: pgService.isNative,
      isSourceInternal: pgService.isSourceInternal,
      isDestinationInternal: pgService.isDestinationInternal,
      needSubscription: pgService.needSubscription,
      currencyCode: pgService.currencyCode,
      useTransactionType: pgService.useTransactionType,
      checkSubscription: pgService.checkSubscription,
      ignoreFees: pgService.ignoreFees,
      ignoreLimit: pgService.ignoreLimit,
      ignoreCommission: pgService.ignoreCommission,
      checkOtp: pgService.checkOtp,
      pgTransactionType1Code: pgService.pgTransactionType1Code,
      pgTransactionType2Code: pgService.pgTransactionType2Code,
      partnerOwnerCode: pgService.partnerOwnerCode,
      transactionTypeCode: pgService.transactionTypeCode,
      serviceAuthenticationCode: pgService.serviceAuthenticationCode,
      contractPath: pgService.contractPath,
      description: pgService.description,
      logic: pgService.logic,
      active: pgService.active,
      sourceConnectorId: pgService.sourceConnectorId,
      destinationConnectorId: pgService.destinationConnectorId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pgService = this.createFromForm();
    if (pgService.id !== undefined) {
      this.subscribeToSaveResponse(this.pgServiceService.update(pgService));
    } else {
      this.subscribeToSaveResponse(this.pgServiceService.create(pgService));
    }
  }

  private createFromForm(): IPgService {
    return {
      ...new PgService(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      isNative: this.editForm.get(['isNative']).value,
      isSourceInternal: this.editForm.get(['isSourceInternal']).value,
      isDestinationInternal: this.editForm.get(['isDestinationInternal']).value,
      needSubscription: this.editForm.get(['needSubscription']).value,
      currencyCode: this.editForm.get(['currencyCode']).value,
      useTransactionType: this.editForm.get(['useTransactionType']).value,
      checkSubscription: this.editForm.get(['checkSubscription']).value,
      ignoreFees: this.editForm.get(['ignoreFees']).value,
      ignoreLimit: this.editForm.get(['ignoreLimit']).value,
      ignoreCommission: this.editForm.get(['ignoreCommission']).value,
      checkOtp: this.editForm.get(['checkOtp']).value,
      pgTransactionType1Code: this.editForm.get(['pgTransactionType1Code']).value,
      pgTransactionType2Code: this.editForm.get(['pgTransactionType2Code']).value,
      partnerOwnerCode: this.editForm.get(['partnerOwnerCode']).value,
      transactionTypeCode: this.editForm.get(['transactionTypeCode']).value,
      serviceAuthenticationCode: this.editForm.get(['serviceAuthenticationCode']).value,
      contractPath: this.editForm.get(['contractPath']).value,
      description: this.editForm.get(['description']).value,
      logic: this.editForm.get(['logic']).value,
      active: this.editForm.get(['active']).value,
      sourceConnectorId: this.editForm.get(['sourceConnectorId']).value,
      destinationConnectorId: this.editForm.get(['destinationConnectorId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPgService>>) {
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

  trackConnectorById(index: number, item: IConnector) {
    return item.id;
  }
}
