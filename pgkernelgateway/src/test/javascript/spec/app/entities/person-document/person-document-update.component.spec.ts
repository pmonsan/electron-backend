/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PersonDocumentUpdateComponent } from 'app/entities/person-document/person-document-update.component';
import { PersonDocumentService } from 'app/entities/person-document/person-document.service';
import { PersonDocument } from 'app/shared/model/person-document.model';

describe('Component Tests', () => {
  describe('PersonDocument Management Update Component', () => {
    let comp: PersonDocumentUpdateComponent;
    let fixture: ComponentFixture<PersonDocumentUpdateComponent>;
    let service: PersonDocumentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PersonDocumentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PersonDocumentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonDocumentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonDocumentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PersonDocument(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PersonDocument();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
