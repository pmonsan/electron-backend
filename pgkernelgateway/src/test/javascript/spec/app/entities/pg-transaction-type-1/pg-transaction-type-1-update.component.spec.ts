/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgTransactionType1UpdateComponent } from 'app/entities/pg-transaction-type-1/pg-transaction-type-1-update.component';
import { PgTransactionType1Service } from 'app/entities/pg-transaction-type-1/pg-transaction-type-1.service';
import { PgTransactionType1 } from 'app/shared/model/pg-transaction-type-1.model';

describe('Component Tests', () => {
  describe('PgTransactionType1 Management Update Component', () => {
    let comp: PgTransactionType1UpdateComponent;
    let fixture: ComponentFixture<PgTransactionType1UpdateComponent>;
    let service: PgTransactionType1Service;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgTransactionType1UpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PgTransactionType1UpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgTransactionType1UpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgTransactionType1Service);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PgTransactionType1(123);
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
        const entity = new PgTransactionType1();
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
