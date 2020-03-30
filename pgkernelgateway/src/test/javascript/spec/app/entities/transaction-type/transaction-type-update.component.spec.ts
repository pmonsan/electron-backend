/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionTypeUpdateComponent } from 'app/entities/transaction-type/transaction-type-update.component';
import { TransactionTypeService } from 'app/entities/transaction-type/transaction-type.service';
import { TransactionType } from 'app/shared/model/transaction-type.model';

describe('Component Tests', () => {
  describe('TransactionType Management Update Component', () => {
    let comp: TransactionTypeUpdateComponent;
    let fixture: ComponentFixture<TransactionTypeUpdateComponent>;
    let service: TransactionTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TransactionTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TransactionType(123);
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
        const entity = new TransactionType();
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
