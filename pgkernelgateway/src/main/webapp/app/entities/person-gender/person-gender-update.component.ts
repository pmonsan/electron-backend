import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPersonGender, PersonGender } from 'app/shared/model/person-gender.model';
import { PersonGenderService } from './person-gender.service';

@Component({
  selector: 'jhi-person-gender-update',
  templateUrl: './person-gender-update.component.html'
})
export class PersonGenderUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    active: [null, [Validators.required]]
  });

  constructor(protected personGenderService: PersonGenderService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ personGender }) => {
      this.updateForm(personGender);
    });
  }

  updateForm(personGender: IPersonGender) {
    this.editForm.patchValue({
      id: personGender.id,
      code: personGender.code,
      label: personGender.label,
      active: personGender.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const personGender = this.createFromForm();
    if (personGender.id !== undefined) {
      this.subscribeToSaveResponse(this.personGenderService.update(personGender));
    } else {
      this.subscribeToSaveResponse(this.personGenderService.create(personGender));
    }
  }

  private createFromForm(): IPersonGender {
    return {
      ...new PersonGender(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonGender>>) {
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
