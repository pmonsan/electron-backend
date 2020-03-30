import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPgChannel, PgChannel } from 'app/shared/model/pg-channel.model';
import { PgChannelService } from './pg-channel.service';

@Component({
  selector: 'jhi-pg-channel-update',
  templateUrl: './pg-channel-update.component.html'
})
export class PgChannelUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    longLabel: [null, [Validators.required, Validators.maxLength(50)]],
    shortLabel: [null, [Validators.required, Validators.maxLength(25)]],
    active: [null, [Validators.required]]
  });

  constructor(protected pgChannelService: PgChannelService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pgChannel }) => {
      this.updateForm(pgChannel);
    });
  }

  updateForm(pgChannel: IPgChannel) {
    this.editForm.patchValue({
      id: pgChannel.id,
      code: pgChannel.code,
      longLabel: pgChannel.longLabel,
      shortLabel: pgChannel.shortLabel,
      active: pgChannel.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pgChannel = this.createFromForm();
    if (pgChannel.id !== undefined) {
      this.subscribeToSaveResponse(this.pgChannelService.update(pgChannel));
    } else {
      this.subscribeToSaveResponse(this.pgChannelService.create(pgChannel));
    }
  }

  private createFromForm(): IPgChannel {
    return {
      ...new PgChannel(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      longLabel: this.editForm.get(['longLabel']).value,
      shortLabel: this.editForm.get(['shortLabel']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPgChannel>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
