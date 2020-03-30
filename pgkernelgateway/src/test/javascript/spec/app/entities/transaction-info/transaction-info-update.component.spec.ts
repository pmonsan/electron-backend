/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionInfoUpdateComponent } from 'app/entities/transaction-info/transaction-info-update.component';
import { TransactionInfoService } from 'app/entities/transaction-info/transaction-info.service';
import { TransactionInfo } from 'app/shared/model/transaction-info.model';

describe('Component Tests', () => {
  describe('TransactionInfo Management Update Component', () => {
    let comp: TransactionInfoUpdateComponent;
    let fixture: ComponentFixture<TransactionInfoUpdateComponent>;
    let service: TransactionInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionInfoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TransactionInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionInfoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionInfoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TransactionInfo(123);
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
        const entity = new TransactionInfo();
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
