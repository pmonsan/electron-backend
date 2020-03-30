import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IPgMessage, PgMessage } from 'app/shared/model/pg-message.model';
import { PgMessageService } from './pg-message.service';
import { IPgMessageModel } from 'app/shared/model/pg-message-model.model';
import { PgMessageModelService } from 'app/entities/pg-message-model';
import { IPgMessageStatus } from 'app/shared/model/pg-message-status.model';
import { PgMessageStatusService } from 'app/entities/pg-message-status';

@Component({
  selector: 'jhi-pg-message-update',
  templateUrl: './pg-message-update.component.html'
})
export class PgMessageUpdateComponent implements OnInit {
  isSaving: boolean;

  pgmessagemodels: IPgMessageModel[];

  pgmessagestatuses: IPgMessageStatus[];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(15)]],
    label: [null, [Validators.required, Validators.maxLength(50)]],
    messageDate: [null, [Validators.required]],
    comment: [],
    active: [null, [Validators.required]],
    pgMessageModelId: [],
    pgMessageStatusId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected pgMessageService: PgMessageService,
    protected pgMessageModelService: PgMessageModelService,
    protected pgMessageStatusService: PgMessageStatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pgMessage }) => {
      this.updateForm(pgMessage);
    });
    this.pgMessageModelService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPgMessageModel[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPgMessageModel[]>) => response.body)
      )
      .subscribe((res: IPgMessageModel[]) => (this.pgmessagemodels = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.pgMessageStatusService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPgMessageStatus[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPgMessageStatus[]>) => response.body)
      )
      .subscribe((res: IPgMessageStatus[]) => (this.pgmessagestatuses = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(pgMessage: IPgMessage) {
    this.editForm.patchValue({
      id: pgMessage.id,
      code: pgMessage.code,
      label: pgMessage.label,
      messageDate: pgMessage.messageDate != null ? pgMessage.messageDate.format(DATE_TIME_FORMAT) : null,
      comment: pgMessage.comment,
      active: pgMessage.active,
      pgMessageModelId: pgMessage.pgMessageModelId,
      pgMessageStatusId: pgMessage.pgMessageStatusId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pgMessage = this.createFromForm();
    if (pgMessage.id !== undefined) {
      this.subscribeToSaveResponse(this.pgMessageService.update(pgMessage));
    } else {
      this.subscribeToSaveResponse(this.pgMessageService.create(pgMessage));
    }
  }

  private createFromForm(): IPgMessage {
    return {
      ...new PgMessage(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      messageDate:
        this.editForm.get(['messageDate']).value != null ? moment(this.editForm.get(['messageDate']).value, DATE_TIME_FORMAT) : undefined,
      comment: this.editForm.get(['comment']).value,
      active: this.editForm.get(['active']).value,
      pgMessageModelId: this.editForm.get(['pgMessageModelId']).value,
      pgMessageStatusId: this.editForm.get(['pgMessageStatusId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPgMessage>>) {
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

  trackPgMessageModelById(index: number, item: IPgMessageModel) {
    return item.id;
  }

  trackPgMessageStatusById(index: number, item: IPgMessageStatus) {
    return item.id;
  }
}
