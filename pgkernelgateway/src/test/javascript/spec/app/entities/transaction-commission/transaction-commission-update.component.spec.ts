/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionCommissionUpdateComponent } from 'app/entities/transaction-commission/transaction-commission-update.component';
import { TransactionCommissionService } from 'app/entities/transaction-commission/transaction-commission.service';
import { TransactionCommission } from 'app/shared/model/transaction-commission.model';

describe('Component Tests', () => {
  describe('TransactionCommission Management Update Component', () => {
    let comp: TransactionCommissionUpdateComponent;
    let fixture: ComponentFixture<TransactionCommissionUpdateComponent>;
    let service: TransactionCommissionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionCommissionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TransactionCommissionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionCommissionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionCommissionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TransactionCommission(123);
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
        const entity = new TransactionCommission();
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
