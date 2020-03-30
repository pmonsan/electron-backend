import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IContractOpposition, ContractOpposition } from 'app/shared/model/contract-opposition.model';
import { ContractOppositionService } from './contract-opposition.service';
import { IContract } from 'app/shared/model/contract.model';
import { ContractService } from 'app/entities/contract';

@Component({
  selector: 'jhi-contract-opposition-update',
  templateUrl: './contract-opposition-update.component.html'
})
export class ContractOppositionUpdateComponent implements OnInit {
  isSaving: boolean;

  contracts: IContract[];

  editForm = this.fb.group({
    id: [],
    number: [null, [Validators.maxLength(25)]],
    isCustomerInitiative: [null, [Validators.required]],
    oppositionDate: [null, [Validators.required]],
    oppositionReason: [null, [Validators.maxLength(255)]],
    comment: [null, [Validators.maxLength(255)]],
    active: [null, [Validators.required]],
    contractId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected contractOppositionService: ContractOppositionService,
    protected contractService: ContractService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ contractOpposition }) => {
      this.updateForm(contractOpposition);
    });
    this.contractService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IContract[]>) => mayBeOk.ok),
        map((response: HttpResponse<IContract[]>) => response.body)
      )
      .subscribe((res: IContract[]) => (this.contracts = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(contractOpposition: IContractOpposition) {
    this.editForm.patchValue({
      id: contractOpposition.id,
      number: contractOpposition.number,
      isCustomerInitiative: contractOpposition.isCustomerInitiative,
      oppositionDate: contractOpposition.oppositionDate != null ? contractOpposition.oppositionDate.format(DATE_TIME_FORMAT) : null,
      oppositionReason: contractOpposition.oppositionReason,
      comment: contractOpposition.comment,
      active: contractOpposition.active,
      contractId: contractOpposition.contractId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const contractOpposition = this.createFromForm();
    if (contractOpposition.id !== undefined) {
      this.subscribeToSaveResponse(this.contractOppositionService.update(contractOpposition));
    } else {
      this.subscribeToSaveResponse(this.contractOppositionService.create(contractOpposition));
    }
  }

  private createFromForm(): IContractOpposition {
    return {
      ...new ContractOpposition(),
      id: this.editForm.get(['id']).value,
      number: this.editForm.get(['number']).value,
      isCustomerInitiative: this.editForm.get(['isCustomerInitiative']).value,
      oppositionDate:
        this.editForm.get(['oppositionDate']).value != null
          ? moment(this.editForm.get(['oppositionDate']).value, DATE_TIME_FORMAT)
          : undefined,
      oppositionReason: this.editForm.get(['oppositionReason']).value,
      comment: this.editForm.get(['comment']).value,
      active: this.editForm.get(['active']).value,
      contractId: this.editForm.get(['contractId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContractOpposition>>) {
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
