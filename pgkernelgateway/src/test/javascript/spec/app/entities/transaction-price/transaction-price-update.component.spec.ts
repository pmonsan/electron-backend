/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionPriceUpdateComponent } from 'app/entities/transaction-price/transaction-price-update.component';
import { TransactionPriceService } from 'app/entities/transaction-price/transaction-price.service';
import { TransactionPrice } from 'app/shared/model/transaction-price.model';

describe('Component Tests', () => {
  describe('TransactionPrice Management Update Component', () => {
    let comp: TransactionPriceUpdateComponent;
    let fixture: ComponentFixture<TransactionPriceUpdateComponent>;
    let service: TransactionPriceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionPriceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TransactionPriceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionPriceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionPriceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TransactionPrice(123);
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
        const entity = new TransactionPrice();
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
