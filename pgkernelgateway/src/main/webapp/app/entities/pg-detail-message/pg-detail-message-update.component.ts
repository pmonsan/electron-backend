import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPgDetailMessage, PgDetailMessage } from 'app/shared/model/pg-detail-message.model';
import { PgDetailMessageService } from './pg-detail-message.service';
import { IPgData } from 'app/shared/model/pg-data.model';
import { PgDataService } from 'app/entities/pg-data';
import { IPgMessage } from 'app/shared/model/pg-message.model';
import { PgMessageService } from 'app/entities/pg-message';

@Component({
  selector: 'jhi-pg-detail-message-update',
  templateUrl: './pg-detail-message-update.component.html'
})
export class PgDetailMessageUpdateComponent implements OnInit {
  isSaving: boolean;

  pgdata: IPgData[];

  pgmessages: IPgMessage[];

  editForm = this.fb.group({
    id: [],
    dataValue: [null, [Validators.required]],
    active: [null, [Validators.required]],
    pgDataId: [],
    pgMessageId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected pgDetailMessageService: PgDetailMessageService,
    protected pgDataService: PgDataService,
    protected pgMessageService: PgMessageService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pgDetailMessage }) => {
      this.updateForm(pgDetailMessage);
    });
    this.pgDataService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPgData[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPgData[]>) => response.body)
      )
      .subscribe((res: IPgData[]) => (this.pgdata = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.pgMessageService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPgMessage[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPgMessage[]>) => response.body)
      )
      .subscribe((res: IPgMessage[]) => (this.pgmessages = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(pgDetailMessage: IPgDetailMessage) {
    this.editForm.patchValue({
      id: pgDetailMessage.id,
      dataValue: pgDetailMessage.dataValue,
      active: pgDetailMessage.active,
      pgDataId: pgDetailMessage.pgDataId,
      pgMessageId: pgDetailMessage.pgMessageId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pgDetailMessage = this.createFromForm();
    if (pgDetailMessage.id !== undefined) {
      this.subscribeToSaveResponse(this.pgDetailMessageService.update(pgDetailMessage));
    } else {
      this.subscribeToSaveResponse(this.pgDetailMessageService.create(pgDetailMessage));
    }
  }

  private createFromForm(): IPgDetailMessage {
    return {
      ...new PgDetailMessage(),
      id: this.editForm.get(['id']).value,
      dataValue: this.editForm.get(['dataValue']).value,
      active: this.editForm.get(['active']).value,
      pgDataId: this.editForm.get(['pgDataId']).value,
      pgMessageId: this.editForm.get(['pgMessageId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPgDetailMessage>>) {
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

  trackPgMessageById(index: number, item: IPgMessage) {
    return item.id;
  }
}
