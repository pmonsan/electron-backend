/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PartnerTypeUpdateComponent } from 'app/entities/partner-type/partner-type-update.component';
import { PartnerTypeService } from 'app/entities/partner-type/partner-type.service';
import { PartnerType } from 'app/shared/model/partner-type.model';

describe('Component Tests', () => {
  describe('PartnerType Management Update Component', () => {
    let comp: PartnerTypeUpdateComponent;
    let fixture: ComponentFixture<PartnerTypeUpdateComponent>;
    let service: PartnerTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PartnerTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PartnerTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartnerTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartnerTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PartnerType(123);
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
        const entity = new PartnerType();
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
