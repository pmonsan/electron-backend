/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionPropertyUpdateComponent } from 'app/entities/transaction-property/transaction-property-update.component';
import { TransactionPropertyService } from 'app/entities/transaction-property/transaction-property.service';
import { TransactionProperty } from 'app/shared/model/transaction-property.model';

describe('Component Tests', () => {
  describe('TransactionProperty Management Update Component', () => {
    let comp: TransactionPropertyUpdateComponent;
    let fixture: ComponentFixture<TransactionPropertyUpdateComponent>;
    let service: TransactionPropertyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionPropertyUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TransactionPropertyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionPropertyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionPropertyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TransactionProperty(123);
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
        const entity = new TransactionProperty();
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
