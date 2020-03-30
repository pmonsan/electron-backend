import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPgModule, PgModule } from 'app/shared/model/pg-module.model';
import { PgModuleService } from './pg-module.service';

@Component({
  selector: 'jhi-pg-module-update',
  templateUrl: './pg-module-update.component.html'
})
export class PgModuleUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(10)]],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    active: [null, [Validators.required]]
  });

  constructor(protected pgModuleService: PgModuleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pgModule }) => {
      this.updateForm(pgModule);
    });
  }

  updateForm(pgModule: IPgModule) {
    this.editForm.patchValue({
      id: pgModule.id,
      code: pgModule.code,
      label: pgModule.label,
      active: pgModule.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pgModule = this.createFromForm();
    if (pgModule.id !== undefined) {
      this.subscribeToSaveResponse(this.pgModuleService.update(pgModule));
    } else {
      this.subscribeToSaveResponse(this.pgModuleService.create(pgModule));
    }
  }

  private createFromForm(): IPgModule {
    return {
      ...new PgModule(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPgModule>>) {
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
