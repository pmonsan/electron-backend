/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionGroupUpdateComponent } from 'app/entities/transaction-group/transaction-group-update.component';
import { TransactionGroupService } from 'app/entities/transaction-group/transaction-group.service';
import { TransactionGroup } from 'app/shared/model/transaction-group.model';

describe('Component Tests', () => {
  describe('TransactionGroup Management Update Component', () => {
    let comp: TransactionGroupUpdateComponent;
    let fixture: ComponentFixture<TransactionGroupUpdateComponent>;
    let service: TransactionGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionGroupUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TransactionGroupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionGroupUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionGroupService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TransactionGroup(123);
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
        const entity = new TransactionGroup();
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
