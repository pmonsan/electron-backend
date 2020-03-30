import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPersonType, PersonType } from 'app/shared/model/person-type.model';
import { PersonTypeService } from './person-type.service';

@Component({
  selector: 'jhi-person-type-update',
  templateUrl: './person-type-update.component.html'
})
export class PersonTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    active: [null, [Validators.required]]
  });

  constructor(protected personTypeService: PersonTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ personType }) => {
      this.updateForm(personType);
    });
  }

  updateForm(personType: IPersonType) {
    this.editForm.patchValue({
      id: personType.id,
      code: personType.code,
      label: personType.label,
      active: personType.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const personType = this.createFromForm();
    if (personType.id !== undefined) {
      this.subscribeToSaveResponse(this.personTypeService.update(personType));
    } else {
      this.subscribeToSaveResponse(this.personTypeService.create(personType));
    }
  }

  private createFromForm(): IPersonType {
    return {
      ...new PersonType(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonType>>) {
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
