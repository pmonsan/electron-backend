import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IPg8583Request, Pg8583Request } from 'app/shared/model/pg-8583-request.model';
import { Pg8583RequestService } from './pg-8583-request.service';

@Component({
  selector: 'jhi-pg-8583-request-update',
  templateUrl: './pg-8583-request-update.component.html'
})
export class Pg8583RequestUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    number: [null, [Validators.required, Validators.maxLength(50)]],
    apiKey: [null, [Validators.required, Validators.maxLength(100)]],
    encryptedData: [null, [Validators.required, Validators.maxLength(255)]],
    decryptedData: [null, [Validators.maxLength(255)]],
    registrationDate: [null, [Validators.required]],
    responseDate: [],
    requestResponse: [null, [Validators.maxLength(255)]],
    status: [null, [Validators.maxLength(100)]],
    reason: [null, [Validators.maxLength(100)]],
    pgapsMessage: [null, [Validators.maxLength(255)]],
    pgapsTransactionNumber: [null, [Validators.maxLength(50)]],
    active: [null, [Validators.required]]
  });

  constructor(protected pg8583RequestService: Pg8583RequestService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pg8583Request }) => {
      this.updateForm(pg8583Request);
    });
  }

  updateForm(pg8583Request: IPg8583Request) {
    this.editForm.patchValue({
      id: pg8583Request.id,
      number: pg8583Request.number,
      apiKey: pg8583Request.apiKey,
      encryptedData: pg8583Request.encryptedData,
      decryptedData: pg8583Request.decryptedData,
      registrationDate: pg8583Request.registrationDate != null ? pg8583Request.registrationDate.format(DATE_TIME_FORMAT) : null,
      responseDate: pg8583Request.responseDate != null ? pg8583Request.responseDate.format(DATE_TIME_FORMAT) : null,
      requestResponse: pg8583Request.requestResponse,
      status: pg8583Request.status,
      reason: pg8583Request.reason,
      pgapsMessage: pg8583Request.pgapsMessage,
      pgapsTransactionNumber: pg8583Request.pgapsTransactionNumber,
      active: pg8583Request.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pg8583Request = this.createFromForm();
    if (pg8583Request.id !== undefined) {
      this.subscribeToSaveResponse(this.pg8583RequestService.update(pg8583Request));
    } else {
      this.subscribeToSaveResponse(this.pg8583RequestService.create(pg8583Request));
    }
  }

  private createFromForm(): IPg8583Request {
    return {
      ...new Pg8583Request(),
      id: this.editForm.get(['id']).value,
      number: this.editForm.get(['number']).value,
      apiKey: this.editForm.get(['apiKey']).value,
      encryptedData: this.editForm.get(['encryptedData']).value,
      decryptedData: this.editForm.get(['decryptedData']).value,
      registrationDate:
        this.editForm.get(['registrationDate']).value != null
          ? moment(this.editForm.get(['registrationDate']).value, DATE_TIME_FORMAT)
          : undefined,
      responseDate:
        this.editForm.get(['responseDate']).value != null ? moment(this.editForm.get(['responseDate']).value, DATE_TIME_FORMAT) : undefined,
      requestResponse: this.editForm.get(['requestResponse']).value,
      status: this.editForm.get(['status']).value,
      reason: this.editForm.get(['reason']).value,
      pgapsMessage: this.editForm.get(['pgapsMessage']).value,
      pgapsTransactionNumber: this.editForm.get(['pgapsTransactionNumber']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPg8583Request>>) {
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
