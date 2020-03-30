import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAgent, Agent } from 'app/shared/model/agent.model';
import { AgentService } from './agent.service';
import { IAgentType } from 'app/shared/model/agent-type.model';
import { AgentTypeService } from 'app/entities/agent-type';

@Component({
  selector: 'jhi-agent-update',
  templateUrl: './agent-update.component.html'
})
export class AgentUpdateComponent implements OnInit {
  isSaving: boolean;

  agenttypes: IAgentType[];

  editForm = this.fb.group({
    id: [],
    matricule: [null, [Validators.maxLength(20)]],
    firstname: [null, [Validators.maxLength(50)]],
    lastname: [null, [Validators.maxLength(50)]],
    birthdate: [null, [Validators.maxLength(10)]],
    email: [null, [Validators.pattern('^[^@s]+@[^@s]+.[^@s]+$')]],
    mobilePhone: [null, [Validators.maxLength(20)]],
    businessEmail: [null, [Validators.required, Validators.maxLength(100), Validators.pattern('^[^@s]+@[^@s]+.[^@s]+$')]],
    businessPhone: [null, [Validators.required, Validators.maxLength(20)]],
    address: [null, [Validators.maxLength(255)]],
    postalCode: [null, [Validators.maxLength(10)]],
    city: [null, [Validators.maxLength(50)]],
    countryCode: [null, [Validators.required, Validators.maxLength(5)]],
    partnerCode: [null, [Validators.required, Validators.maxLength(5)]],
    username: [null, [Validators.maxLength(50)]],
    active: [null, [Validators.required]],
    agentTypeId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected agentService: AgentService,
    protected agentTypeService: AgentTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ agent }) => {
      this.updateForm(agent);
    });
    this.agentTypeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAgentType[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAgentType[]>) => response.body)
      )
      .subscribe((res: IAgentType[]) => (this.agenttypes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(agent: IAgent) {
    this.editForm.patchValue({
      id: agent.id,
      matricule: agent.matricule,
      firstname: agent.firstname,
      lastname: agent.lastname,
      birthdate: agent.birthdate,
      email: agent.email,
      mobilePhone: agent.mobilePhone,
      businessEmail: agent.businessEmail,
      businessPhone: agent.businessPhone,
      address: agent.address,
      postalCode: agent.postalCode,
      city: agent.city,
      countryCode: agent.countryCode,
      partnerCode: agent.partnerCode,
      username: agent.username,
      active: agent.active,
      agentTypeId: agent.agentTypeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const agent = this.createFromForm();
    if (agent.id !== undefined) {
      this.subscribeToSaveResponse(this.agentService.update(agent));
    } else {
      this.subscribeToSaveResponse(this.agentService.create(agent));
    }
  }

  private createFromForm(): IAgent {
    return {
      ...new Agent(),
      id: this.editForm.get(['id']).value,
      matricule: this.editForm.get(['matricule']).value,
      firstname: this.editForm.get(['firstname']).value,
      lastname: this.editForm.get(['lastname']).value,
      birthdate: this.editForm.get(['birthdate']).value,
      email: this.editForm.get(['email']).value,
      mobilePhone: this.editForm.get(['mobilePhone']).value,
      businessEmail: this.editForm.get(['businessEmail']).value,
      businessPhone: this.editForm.get(['businessPhone']).value,
      address: this.editForm.get(['address']).value,
      postalCode: this.editForm.get(['postalCode']).value,
      city: this.editForm.get(['city']).value,
      countryCode: this.editForm.get(['countryCode']).value,
      partnerCode: this.editForm.get(['partnerCode']).value,
      username: this.editForm.get(['username']).value,
      active: this.editForm.get(['active']).value,
      agentTypeId: this.editForm.get(['agentTypeId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgent>>) {
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

  trackAgentTypeById(index: number, item: IAgentType) {
    return item.id;
  }
}
