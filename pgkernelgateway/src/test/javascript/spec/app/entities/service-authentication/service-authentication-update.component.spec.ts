/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ServiceAuthenticationUpdateComponent } from 'app/entities/service-authentication/service-authentication-update.component';
import { ServiceAuthenticationService } from 'app/entities/service-authentication/service-authentication.service';
import { ServiceAuthentication } from 'app/shared/model/service-authentication.model';

describe('Component Tests', () => {
  describe('ServiceAuthentication Management Update Component', () => {
    let comp: ServiceAuthenticationUpdateComponent;
    let fixture: ComponentFixture<ServiceAuthenticationUpdateComponent>;
    let service: ServiceAuthenticationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ServiceAuthenticationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServiceAuthenticationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceAuthenticationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceAuthenticationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceAuthentication(123);
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
        const entity = new ServiceAuthentication();
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
