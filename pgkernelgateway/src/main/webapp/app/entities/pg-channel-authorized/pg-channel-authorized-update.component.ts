import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IPgChannelAuthorized, PgChannelAuthorized } from 'app/shared/model/pg-channel-authorized.model';
import { PgChannelAuthorizedService } from './pg-channel-authorized.service';
import { IPgChannel } from 'app/shared/model/pg-channel.model';
import { PgChannelService } from 'app/entities/pg-channel';

@Component({
  selector: 'jhi-pg-channel-authorized-update',
  templateUrl: './pg-channel-authorized-update.component.html'
})
export class PgChannelAuthorizedUpdateComponent implements OnInit {
  isSaving: boolean;

  pgchannels: IPgChannel[];

  editForm = this.fb.group({
    id: [],
    transactionTypeCode: [null, [Validators.required, Validators.maxLength(5)]],
    registrationDate: [null, [Validators.required]],
    active: [null, [Validators.required]],
    pgChannelId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected pgChannelAuthorizedService: PgChannelAuthorizedService,
    protected pgChannelService: PgChannelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pgChannelAuthorized }) => {
      this.updateForm(pgChannelAuthorized);
    });
    this.pgChannelService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPgChannel[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPgChannel[]>) => response.body)
      )
      .subscribe((res: IPgChannel[]) => (this.pgchannels = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(pgChannelAuthorized: IPgChannelAuthorized) {
    this.editForm.patchValue({
      id: pgChannelAuthorized.id,
      transactionTypeCode: pgChannelAuthorized.transactionTypeCode,
      registrationDate: pgChannelAuthorized.registrationDate != null ? pgChannelAuthorized.registrationDate.format(DATE_TIME_FORMAT) : null,
      active: pgChannelAuthorized.active,
      pgChannelId: pgChannelAuthorized.pgChannelId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pgChannelAuthorized = this.createFromForm();
    if (pgChannelAuthorized.id !== undefined) {
      this.subscribeToSaveResponse(this.pgChannelAuthorizedService.update(pgChannelAuthorized));
    } else {
      this.subscribeToSaveResponse(this.pgChannelAuthorizedService.create(pgChannelAuthorized));
    }
  }

  private createFromForm(): IPgChannelAuthorized {
    return {
      ...new PgChannelAuthorized(),
      id: this.editForm.get(['id']).value,
      transactionTypeCode: this.editForm.get(['transactionTypeCode']).value,
      registrationDate:
        this.editForm.get(['registrationDate']).value != null
          ? moment(this.editForm.get(['registrationDate']).value, DATE_TIME_FORMAT)
          : undefined,
      active: this.editForm.get(['active']).value,
      pgChannelId: this.editForm.get(['pgChannelId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPgChannelAuthorized>>) {
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

  trackPgChannelById(index: number, item: IPgChannel) {
    return item.id;
  }
}
