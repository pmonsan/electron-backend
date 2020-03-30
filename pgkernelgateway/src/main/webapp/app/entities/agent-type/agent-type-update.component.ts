import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAgentType, AgentType } from 'app/shared/model/agent-type.model';
import { AgentTypeService } from './agent-type.service';

@Component({
  selector: 'jhi-agent-type-update',
  templateUrl: './agent-type-update.component.html'
})
export class AgentTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    active: [null, [Validators.required]]
  });

  constructor(protected agentTypeService: AgentTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ agentType }) => {
      this.updateForm(agentType);
    });
  }

  updateForm(agentType: IAgentType) {
    this.editForm.patchValue({
      id: agentType.id,
      label: agentType.label,
      active: agentType.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const agentType = this.createFromForm();
    if (agentType.id !== undefined) {
      this.subscribeToSaveResponse(this.agentTypeService.update(agentType));
    } else {
      this.subscribeToSaveResponse(this.agentTypeService.create(agentType));
    }
  }

  private createFromForm(): IAgentType {
    return {
      ...new AgentType(),
      id: this.editForm.get(['id']).value,
      label: this.editForm.get(['label']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgentType>>) {
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
