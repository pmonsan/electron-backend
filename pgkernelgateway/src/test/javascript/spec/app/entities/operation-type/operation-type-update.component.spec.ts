/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { OperationTypeUpdateComponent } from 'app/entities/operation-type/operation-type-update.component';
import { OperationTypeService } from 'app/entities/operation-type/operation-type.service';
import { OperationType } from 'app/shared/model/operation-type.model';

describe('Component Tests', () => {
  describe('OperationType Management Update Component', () => {
    let comp: OperationTypeUpdateComponent;
    let fixture: ComponentFixture<OperationTypeUpdateComponent>;
    let service: OperationTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [OperationTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OperationTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OperationTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OperationTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OperationType(123);
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
        const entity = new OperationType();
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
