/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ServiceIntegrationUpdateComponent } from 'app/entities/service-integration/service-integration-update.component';
import { ServiceIntegrationService } from 'app/entities/service-integration/service-integration.service';
import { ServiceIntegration } from 'app/shared/model/service-integration.model';

describe('Component Tests', () => {
  describe('ServiceIntegration Management Update Component', () => {
    let comp: ServiceIntegrationUpdateComponent;
    let fixture: ComponentFixture<ServiceIntegrationUpdateComponent>;
    let service: ServiceIntegrationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ServiceIntegrationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServiceIntegrationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceIntegrationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceIntegrationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceIntegration(123);
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
        const entity = new ServiceIntegration();
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
