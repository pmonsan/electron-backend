import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDetailContract, DetailContract } from 'app/shared/model/detail-contract.model';
import { DetailContractService } from './detail-contract.service';
import { IContract } from 'app/shared/model/contract.model';
import { ContractService } from 'app/entities/contract';

@Component({
  selector: 'jhi-detail-contract-update',
  templateUrl: './detail-contract-update.component.html'
})
export class DetailContractUpdateComponent implements OnInit {
  isSaving: boolean;

  contracts: IContract[];

  editForm = this.fb.group({
    id: [],
    comment: [null, [Validators.maxLength(255)]],
    active: [null, [Validators.required]],
    contractId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected detailContractService: DetailContractService,
    protected contractService: ContractService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ detailContract }) => {
      this.updateForm(detailContract);
    });
    this.contractService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IContract[]>) => mayBeOk.ok),
        map((response: HttpResponse<IContract[]>) => response.body)
      )
      .subscribe((res: IContract[]) => (this.contracts = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(detailContract: IDetailContract) {
    this.editForm.patchValue({
      id: detailContract.id,
      comment: detailContract.comment,
      active: detailContract.active,
      contractId: detailContract.contractId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const detailContract = this.createFromForm();
    if (detailContract.id !== undefined) {
      this.subscribeToSaveResponse(this.detailContractService.update(detailContract));
    } else {
      this.subscribeToSaveResponse(this.detailContractService.create(detailContract));
    }
  }

  private createFromForm(): IDetailContract {
    return {
      ...new DetailContract(),
      id: this.editForm.get(['id']).value,
      comment: this.editForm.get(['comment']).value,
      active: this.editForm.get(['active']).value,
      contractId: this.editForm.get(['contractId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetailContract>>) {
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

  trackContractById(index: number, item: IContract) {
    return item.id;
  }
}
