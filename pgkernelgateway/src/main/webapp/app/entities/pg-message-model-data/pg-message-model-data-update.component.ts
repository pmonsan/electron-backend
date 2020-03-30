import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPgMessageModelData, PgMessageModelData } from 'app/shared/model/pg-message-model-data.model';
import { PgMessageModelDataService } from './pg-message-model-data.service';
import { IPgData } from 'app/shared/model/pg-data.model';
import { PgDataService } from 'app/entities/pg-data';
import { IPgMessageModel } from 'app/shared/model/pg-message-model.model';
import { PgMessageModelService } from 'app/entities/pg-message-model';

@Component({
  selector: 'jhi-pg-message-model-data-update',
  templateUrl: './pg-message-model-data-update.component.html'
})
export class PgMessageModelDataUpdateComponent implements OnInit {
  isSaving: boolean;

  pgdata: IPgData[];

  pgmessagemodels: IPgMessageModel[];

  editForm = this.fb.group({
    id: [],
    mandatory: [null, [Validators.required]],
    hidden: [null, [Validators.required]],
    active: [null, [Validators.required]],
    pgDataId: [],
    pgMessageModelId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected pgMessageModelDataService: PgMessageModelDataService,
    protected pgDataService: PgDataService,
    protected pgMessageModelService: PgMessageModelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pgMessageModelData }) => {
      this.updateForm(pgMessageModelData);
    });
    this.pgDataService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPgData[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPgData[]>) => response.body)
      )
      .subscribe((res: IPgData[]) => (this.pgdata = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.pgMessageModelService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPgMessageModel[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPgMessageModel[]>) => response.body)
      )
      .subscribe((res: IPgMessageModel[]) => (this.pgmessagemodels = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(pgMessageModelData: IPgMessageModelData) {
    this.editForm.patchValue({
      id: pgMessageModelData.id,
      mandatory: pgMessageModelData.mandatory,
      hidden: pgMessageModelData.hidden,
      active: pgMessageModelData.active,
      pgDataId: pgMessageModelData.pgDataId,
      pgMessageModelId: pgMessageModelData.pgMessageModelId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pgMessageModelData = this.createFromForm();
    if (pgMessageModelData.id !== undefined) {
      this.subscribeToSaveResponse(this.pgMessageModelDataService.update(pgMessageModelData));
    } else {
      this.subscribeToSaveResponse(this.pgMessageModelDataService.create(pgMessageModelData));
    }
  }

  private createFromForm(): IPgMessageModelData {
    return {
      ...new PgMessageModelData(),
      id: this.editForm.get(['id']).value,
      mandatory: this.editForm.get(['mandatory']).value,
      hidden: this.editForm.get(['hidden']).value,
      active: this.editForm.get(['active']).value,
      pgDataId: this.editForm.get(['pgDataId']).value,
      pgMessageModelId: this.editForm.get(['pgMessageModelId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPgMessageModelData>>) {
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

  trackPgDataById(index: number, item: IPgData) {
    return item.id;
  }

  trackPgMessageModelById(index: number, item: IPgMessageModel) {
    return item.id;
  }
}
