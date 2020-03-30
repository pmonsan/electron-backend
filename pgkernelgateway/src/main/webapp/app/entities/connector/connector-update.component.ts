import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IConnector, Connector } from 'app/shared/model/connector.model';
import { ConnectorService } from './connector.service';
import { IConnectorType } from 'app/shared/model/connector-type.model';
import { ConnectorTypeService } from 'app/entities/connector-type';
import { IPgModule } from 'app/shared/model/pg-module.model';
import { PgModuleService } from 'app/entities/pg-module';

@Component({
  selector: 'jhi-connector-update',
  templateUrl: './connector-update.component.html'
})
export class ConnectorUpdateComponent implements OnInit {
  isSaving: boolean;

  connectortypes: IConnectorType[];

  pgmodules: IPgModule[];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(10)]],
    label: [null, [Validators.required, Validators.maxLength(50)]],
    logic: [],
    comment: [],
    partnerCode: [null, [Validators.maxLength(5)]],
    meansofpaymentTypeCode: [null, [Validators.required, Validators.maxLength(5)]],
    active: [null, [Validators.required]],
    connectorTypeId: [],
    pgModuleId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected connectorService: ConnectorService,
    protected connectorTypeService: ConnectorTypeService,
    protected pgModuleService: PgModuleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ connector }) => {
      this.updateForm(connector);
    });
    this.connectorTypeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IConnectorType[]>) => mayBeOk.ok),
        map((response: HttpResponse<IConnectorType[]>) => response.body)
      )
      .subscribe((res: IConnectorType[]) => (this.connectortypes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.pgModuleService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPgModule[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPgModule[]>) => response.body)
      )
      .subscribe((res: IPgModule[]) => (this.pgmodules = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(connector: IConnector) {
    this.editForm.patchValue({
      id: connector.id,
      code: connector.code,
      label: connector.label,
      logic: connector.logic,
      comment: connector.comment,
      partnerCode: connector.partnerCode,
      meansofpaymentTypeCode: connector.meansofpaymentTypeCode,
      active: connector.active,
      connectorTypeId: connector.connectorTypeId,
      pgModuleId: connector.pgModuleId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const connector = this.createFromForm();
    if (connector.id !== undefined) {
      this.subscribeToSaveResponse(this.connectorService.update(connector));
    } else {
      this.subscribeToSaveResponse(this.connectorService.create(connector));
    }
  }

  private createFromForm(): IConnector {
    return {
      ...new Connector(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      logic: this.editForm.get(['logic']).value,
      comment: this.editForm.get(['comment']).value,
      partnerCode: this.editForm.get(['partnerCode']).value,
      meansofpaymentTypeCode: this.editForm.get(['meansofpaymentTypeCode']).value,
      active: this.editForm.get(['active']).value,
      connectorTypeId: this.editForm.get(['connectorTypeId']).value,
      pgModuleId: this.editForm.get(['pgModuleId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConnector>>) {
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

  trackConnectorTypeById(index: number, item: IConnectorType) {
    return item.id;
  }

  trackPgModuleById(index: number, item: IPgModule) {
    return item.id;
  }
}
