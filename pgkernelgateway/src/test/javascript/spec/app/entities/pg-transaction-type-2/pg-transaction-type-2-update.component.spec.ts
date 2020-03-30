/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgTransactionType2UpdateComponent } from 'app/entities/pg-transaction-type-2/pg-transaction-type-2-update.component';
import { PgTransactionType2Service } from 'app/entities/pg-transaction-type-2/pg-transaction-type-2.service';
import { PgTransactionType2 } from 'app/shared/model/pg-transaction-type-2.model';

describe('Component Tests', () => {
  describe('PgTransactionType2 Management Update Component', () => {
    let comp: PgTransactionType2UpdateComponent;
    let fixture: ComponentFixture<PgTransactionType2UpdateComponent>;
    let service: PgTransactionType2Service;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgTransactionType2UpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PgTransactionType2UpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgTransactionType2UpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgTransactionType2Service);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PgTransactionType2(123);
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
        const entity = new PgTransactionType2();
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
