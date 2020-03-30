import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ILimitType, LimitType } from 'app/shared/model/limit-type.model';
import { LimitTypeService } from './limit-type.service';
import { IPeriodicity } from 'app/shared/model/periodicity.model';
import { PeriodicityService } from 'app/entities/periodicity';
import { ILimitMeasure } from 'app/shared/model/limit-measure.model';
import { LimitMeasureService } from 'app/entities/limit-measure';

@Component({
  selector: 'jhi-limit-type-update',
  templateUrl: './limit-type-update.component.html'
})
export class LimitTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  periodicities: IPeriodicity[];

  limitmeasures: ILimitMeasure[];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    limitValueType: [null, [Validators.required]],
    active: [null, [Validators.required]],
    periodicityId: [],
    limitMeasureId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected limitTypeService: LimitTypeService,
    protected periodicityService: PeriodicityService,
    protected limitMeasureService: LimitMeasureService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ limitType }) => {
      this.updateForm(limitType);
    });
    this.periodicityService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPeriodicity[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPeriodicity[]>) => response.body)
      )
      .subscribe((res: IPeriodicity[]) => (this.periodicities = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.limitMeasureService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILimitMeasure[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILimitMeasure[]>) => response.body)
      )
      .subscribe((res: ILimitMeasure[]) => (this.limitmeasures = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(limitType: ILimitType) {
    this.editForm.patchValue({
      id: limitType.id,
      code: limitType.code,
      label: limitType.label,
      limitValueType: limitType.limitValueType,
      active: limitType.active,
      periodicityId: limitType.periodicityId,
      limitMeasureId: limitType.limitMeasureId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const limitType = this.createFromForm();
    if (limitType.id !== undefined) {
      this.subscribeToSaveResponse(this.limitTypeService.update(limitType));
    } else {
      this.subscribeToSaveResponse(this.limitTypeService.create(limitType));
    }
  }

  private createFromForm(): ILimitType {
    return {
      ...new LimitType(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      limitValueType: this.editForm.get(['limitValueType']).value,
      active: this.editForm.get(['active']).value,
      periodicityId: this.editForm.get(['periodicityId']).value,
      limitMeasureId: this.editForm.get(['limitMeasureId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILimitType>>) {
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

  trackPeriodicityById(index: number, item: IPeriodicity) {
    return item.id;
  }

  trackLimitMeasureById(index: number, item: ILimitMeasure) {
    return item.id;
  }
}
