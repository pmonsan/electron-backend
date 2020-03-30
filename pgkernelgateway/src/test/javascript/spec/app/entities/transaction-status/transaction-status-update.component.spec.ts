/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionStatusUpdateComponent } from 'app/entities/transaction-status/transaction-status-update.component';
import { TransactionStatusService } from 'app/entities/transaction-status/transaction-status.service';
import { TransactionStatus } from 'app/shared/model/transaction-status.model';

describe('Component Tests', () => {
  describe('TransactionStatus Management Update Component', () => {
    let comp: TransactionStatusUpdateComponent;
    let fixture: ComponentFixture<TransactionStatusUpdateComponent>;
    let service: TransactionStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionStatusUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TransactionStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionStatusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionStatusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TransactionStatus(123);
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
        const entity = new TransactionStatus();
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
