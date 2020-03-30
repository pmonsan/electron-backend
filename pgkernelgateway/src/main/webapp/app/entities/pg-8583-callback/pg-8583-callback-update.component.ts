import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPg8583Callback, Pg8583Callback } from 'app/shared/model/pg-8583-callback.model';
import { Pg8583CallbackService } from './pg-8583-callback.service';

@Component({
  selector: 'jhi-pg-8583-callback-update',
  templateUrl: './pg-8583-callback-update.component.html'
})
export class Pg8583CallbackUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    partnerCode: [null, [Validators.maxLength(5)]],
    callbackUri: [null, [Validators.required, Validators.maxLength(255)]],
    httpMethod: [null, [Validators.required, Validators.maxLength(10)]],
    managerClass: [null, [Validators.required, Validators.maxLength(255)]],
    active: [null, [Validators.required]]
  });

  constructor(protected pg8583CallbackService: Pg8583CallbackService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pg8583Callback }) => {
      this.updateForm(pg8583Callback);
    });
  }

  updateForm(pg8583Callback: IPg8583Callback) {
    this.editForm.patchValue({
      id: pg8583Callback.id,
      partnerCode: pg8583Callback.partnerCode,
      callbackUri: pg8583Callback.callbackUri,
      httpMethod: pg8583Callback.httpMethod,
      managerClass: pg8583Callback.managerClass,
      active: pg8583Callback.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pg8583Callback = this.createFromForm();
    if (pg8583Callback.id !== undefined) {
      this.subscribeToSaveResponse(this.pg8583CallbackService.update(pg8583Callback));
    } else {
      this.subscribeToSaveResponse(this.pg8583CallbackService.create(pg8583Callback));
    }
  }

  private createFromForm(): IPg8583Callback {
    return {
      ...new Pg8583Callback(),
      id: this.editForm.get(['id']).value,
      partnerCode: this.editForm.get(['partnerCode']).value,
      callbackUri: this.editForm.get(['callbackUri']).value,
      httpMethod: this.editForm.get(['httpMethod']).value,
      managerClass: this.editForm.get(['managerClass']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPg8583Callback>>) {
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
