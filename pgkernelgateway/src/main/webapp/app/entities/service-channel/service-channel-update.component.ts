import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IServiceChannel, ServiceChannel } from 'app/shared/model/service-channel.model';
import { ServiceChannelService } from './service-channel.service';
import { IPgService } from 'app/shared/model/pg-service.model';
import { PgServiceService } from 'app/entities/pg-service';

@Component({
  selector: 'jhi-service-channel-update',
  templateUrl: './service-channel-update.component.html'
})
export class ServiceChannelUpdateComponent implements OnInit {
  isSaving: boolean;

  pgservices: IPgService[];

  editForm = this.fb.group({
    id: [],
    channelCode: [null, [Validators.required, Validators.maxLength(5)]],
    active: [null, [Validators.required]],
    pgServiceId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected serviceChannelService: ServiceChannelService,
    protected pgServiceService: PgServiceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceChannel }) => {
      this.updateForm(serviceChannel);
    });
    this.pgServiceService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPgService[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPgService[]>) => response.body)
      )
      .subscribe((res: IPgService[]) => (this.pgservices = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(serviceChannel: IServiceChannel) {
    this.editForm.patchValue({
      id: serviceChannel.id,
      channelCode: serviceChannel.channelCode,
      active: serviceChannel.active,
      pgServiceId: serviceChannel.pgServiceId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const serviceChannel = this.createFromForm();
    if (serviceChannel.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceChannelService.update(serviceChannel));
    } else {
      this.subscribeToSaveResponse(this.serviceChannelService.create(serviceChannel));
    }
  }

  private createFromForm(): IServiceChannel {
    return {
      ...new ServiceChannel(),
      id: this.editForm.get(['id']).value,
      channelCode: this.editForm.get(['channelCode']).value,
      active: this.editForm.get(['active']).value,
      pgServiceId: this.editForm.get(['pgServiceId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceChannel>>) {
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
