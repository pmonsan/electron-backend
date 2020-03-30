/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PartnerSecurityUpdateComponent } from 'app/entities/partner-security/partner-security-update.component';
import { PartnerSecurityService } from 'app/entities/partner-security/partner-security.service';
import { PartnerSecurity } from 'app/shared/model/partner-security.model';

describe('Component Tests', () => {
  describe('PartnerSecurity Management Update Component', () => {
    let comp: PartnerSecurityUpdateComponent;
    let fixture: ComponentFixture<PartnerSecurityUpdateComponent>;
    let service: PartnerSecurityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PartnerSecurityUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PartnerSecurityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartnerSecurityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartnerSecurityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PartnerSecurity(123);
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
        const entity = new PartnerSecurity();
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
