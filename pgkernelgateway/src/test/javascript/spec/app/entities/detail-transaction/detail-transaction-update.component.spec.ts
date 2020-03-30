/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { DetailTransactionUpdateComponent } from 'app/entities/detail-transaction/detail-transaction-update.component';
import { DetailTransactionService } from 'app/entities/detail-transaction/detail-transaction.service';
import { DetailTransaction } from 'app/shared/model/detail-transaction.model';

describe('Component Tests', () => {
  describe('DetailTransaction Management Update Component', () => {
    let comp: DetailTransactionUpdateComponent;
    let fixture: ComponentFixture<DetailTransactionUpdateComponent>;
    let service: DetailTransactionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [DetailTransactionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DetailTransactionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DetailTransactionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DetailTransactionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DetailTransaction(123);
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
        const entity = new DetailTransaction();
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
