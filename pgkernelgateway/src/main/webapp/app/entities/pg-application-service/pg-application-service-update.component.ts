import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPgApplicationService, PgApplicationService } from 'app/shared/model/pg-application-service.model';
import { PgApplicationServiceService } from './pg-application-service.service';
import { IPgApplication } from 'app/shared/model/pg-application.model';
import { PgApplicationService } from 'app/entities/pg-application';

@Component({
  selector: 'jhi-pg-application-service-update',
  templateUrl: './pg-application-service-update.component.html'
})
export class PgApplicationServiceUpdateComponent implements OnInit {
  isSaving: boolean;

  pgapplications: IPgApplication[];

  editForm = this.fb.group({
    id: [],
    serviceCode: [null, [Validators.required, Validators.maxLength(5)]],
    active: [null, [Validators.required]],
    pgApplicationId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected pgApplicationServiceService: PgApplicationServiceService,
    protected pgApplicationService: PgApplicationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pgApplicationService }) => {
      this.updateForm(pgApplicationService);
    });
    this.pgApplicationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPgApplication[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPgApplication[]>) => response.body)
      )
      .subscribe((res: IPgApplication[]) => (this.pgapplications = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(pgApplicationService: IPgApplicationService) {
    this.editForm.patchValue({
      id: pgApplicationService.id,
      serviceCode: pgApplicationService.serviceCode,
      active: pgApplicationService.active,
      pgApplicationId: pgApplicationService.pgApplicationId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pgApplicationService = this.createFromForm();
    if (pgApplicationService.id !== undefined) {
      this.subscribeToSaveResponse(this.pgApplicationServiceService.update(pgApplicationService));
    } else {
      this.subscribeToSaveResponse(this.pgApplicationServiceService.create(pgApplicationService));
    }
  }

  private createFromForm(): IPgApplicationService {
    return {
      ...new PgApplicationService(),
      id: this.editForm.get(['id']).value,
      serviceCode: this.editForm.get(['serviceCode']).value,
      active: this.editForm.get(['active']).value,
      pgApplicationId: this.editForm.get(['pgApplicationId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPgApplicationService>>) {
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

  trackPgApplicationById(index: number, item: IPgApplication) {
    return item.id;
  }
}
