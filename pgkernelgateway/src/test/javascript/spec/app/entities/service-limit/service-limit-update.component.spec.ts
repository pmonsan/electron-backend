/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ServiceLimitUpdateComponent } from 'app/entities/service-limit/service-limit-update.component';
import { ServiceLimitService } from 'app/entities/service-limit/service-limit.service';
import { ServiceLimit } from 'app/shared/model/service-limit.model';

describe('Component Tests', () => {
  describe('ServiceLimit Management Update Component', () => {
    let comp: ServiceLimitUpdateComponent;
    let fixture: ComponentFixture<ServiceLimitUpdateComponent>;
    let service: ServiceLimitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ServiceLimitUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServiceLimitUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceLimitUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceLimitService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceLimit(123);
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
        const entity = new ServiceLimit();
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
