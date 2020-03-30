/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { CustomerTypeUpdateComponent } from 'app/entities/customer-type/customer-type-update.component';
import { CustomerTypeService } from 'app/entities/customer-type/customer-type.service';
import { CustomerType } from 'app/shared/model/customer-type.model';

describe('Component Tests', () => {
  describe('CustomerType Management Update Component', () => {
    let comp: CustomerTypeUpdateComponent;
    let fixture: ComponentFixture<CustomerTypeUpdateComponent>;
    let service: CustomerTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [CustomerTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CustomerTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CustomerType(123);
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
        const entity = new CustomerType();
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
