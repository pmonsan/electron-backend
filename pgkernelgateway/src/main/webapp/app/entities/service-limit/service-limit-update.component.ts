import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IServiceLimit, ServiceLimit } from 'app/shared/model/service-limit.model';
import { ServiceLimitService } from './service-limit.service';
import { IPgService } from 'app/shared/model/pg-service.model';
import { PgServiceService } from 'app/entities/pg-service';

@Component({
  selector: 'jhi-service-limit-update',
  templateUrl: './service-limit-update.component.html'
})
export class ServiceLimitUpdateComponent implements OnInit {
  isSaving: boolean;

  pgservices: IPgService[];

  editForm = this.fb.group({
    id: [],
    limitTypeCode: [null, [Validators.required, Validators.maxLength(5)]],
    value: [null, [Validators.required]],
    comment: [null, [Validators.maxLength(255)]],
    active: [null, [Validators.required]],
    pgServiceId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected serviceLimitService: ServiceLimitService,
    protected pgServiceService: PgServiceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceLimit }) => {
      this.updateForm(serviceLimit);
    });
    this.pgServiceService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPgService[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPgService[]>) => response.body)
      )
      .subscribe((res: IPgService[]) => (this.pgservices = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(serviceLimit: IServiceLimit) {
    this.editForm.patchValue({
      id: serviceLimit.id,
      limitTypeCode: serviceLimit.limitTypeCode,
      value: serviceLimit.value,
      comment: serviceLimit.comment,
      active: serviceLimit.active,
      pgServiceId: serviceLimit.pgServiceId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const serviceLimit = this.createFromForm();
    if (serviceLimit.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceLimitService.update(serviceLimit));
    } else {
      this.subscribeToSaveResponse(this.serviceLimitService.create(serviceLimit));
    }
  }

  private createFromForm(): IServiceLimit {
    return {
      ...new ServiceLimit(),
      id: this.editForm.get(['id']).value,
      limitTypeCode: this.editForm.get(['limitTypeCode']).value,
      value: this.editForm.get(['value']).value,
      comment: this.editForm.get(['comment']).value,
      active: this.editForm.get(['active']).value,
      pgServiceId: this.editForm.get(['pgServiceId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceLimit>>) {
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

  trackPgServiceById(index: number, item: IPgService) {
    return item.id;
  }
}
