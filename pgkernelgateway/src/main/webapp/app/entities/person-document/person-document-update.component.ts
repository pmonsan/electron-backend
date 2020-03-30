import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPersonDocument, PersonDocument } from 'app/shared/model/person-document.model';
import { PersonDocumentService } from './person-document.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer';

@Component({
  selector: 'jhi-person-document-update',
  templateUrl: './person-document-update.component.html'
})
export class PersonDocumentUpdateComponent implements OnInit {
  isSaving: boolean;

  customers: ICustomer[];

  editForm = this.fb.group({
    id: [],
    documentNumber: [null, [Validators.maxLength(50)]],
    expirationDate: [null, [Validators.maxLength(10)]],
    isValid: [null, [Validators.required]],
    documentTypeCode: [null, [Validators.required, Validators.maxLength(5)]],
    active: [null, [Validators.required]],
    customerId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected personDocumentService: PersonDocumentService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ personDocument }) => {
      this.updateForm(personDocument);
    });
    this.customerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICustomer[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICustomer[]>) => response.body)
      )
      .subscribe((res: ICustomer[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(personDocument: IPersonDocument) {
    this.editForm.patchValue({
      id: personDocument.id,
      documentNumber: personDocument.documentNumber,
      expirationDate: personDocument.expirationDate,
      isValid: personDocument.isValid,
      documentTypeCode: personDocument.documentTypeCode,
      active: personDocument.active,
      customerId: personDocument.customerId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const personDocument = this.createFromForm();
    if (personDocument.id !== undefined) {
      this.subscribeToSaveResponse(this.personDocumentService.update(personDocument));
    } else {
      this.subscribeToSaveResponse(this.personDocumentService.create(personDocument));
    }
  }

  private createFromForm(): IPersonDocument {
    return {
      ...new PersonDocument(),
      id: this.editForm.get(['id']).value,
      documentNumber: this.editForm.get(['documentNumber']).value,
      expirationDate: this.editForm.get(['expirationDate']).value,
      isValid: this.editForm.get(['isValid']).value,
      documentTypeCode: this.editForm.get(['documentTypeCode']).value,
      active: this.editForm.get(['active']).value,
      customerId: this.editForm.get(['customerId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonDocument>>) {
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

  trackCustomerById(index: number, item: ICustomer) {
    return item.id;
  }
}
