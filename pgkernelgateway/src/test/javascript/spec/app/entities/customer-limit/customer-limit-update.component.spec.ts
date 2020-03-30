/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { CustomerLimitUpdateComponent } from 'app/entities/customer-limit/customer-limit-update.component';
import { CustomerLimitService } from 'app/entities/customer-limit/customer-limit.service';
import { CustomerLimit } from 'app/shared/model/customer-limit.model';

describe('Component Tests', () => {
  describe('CustomerLimit Management Update Component', () => {
    let comp: CustomerLimitUpdateComponent;
    let fixture: ComponentFixture<CustomerLimitUpdateComponent>;
    let service: CustomerLimitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [CustomerLimitUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CustomerLimitUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerLimitUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerLimitService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CustomerLimit(123);
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
        const entity = new CustomerLimit();
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
