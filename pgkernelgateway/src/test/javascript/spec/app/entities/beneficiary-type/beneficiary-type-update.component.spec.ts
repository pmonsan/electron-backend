/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { BeneficiaryTypeUpdateComponent } from 'app/entities/beneficiary-type/beneficiary-type-update.component';
import { BeneficiaryTypeService } from 'app/entities/beneficiary-type/beneficiary-type.service';
import { BeneficiaryType } from 'app/shared/model/beneficiary-type.model';

describe('Component Tests', () => {
  describe('BeneficiaryType Management Update Component', () => {
    let comp: BeneficiaryTypeUpdateComponent;
    let fixture: ComponentFixture<BeneficiaryTypeUpdateComponent>;
    let service: BeneficiaryTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [BeneficiaryTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BeneficiaryTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BeneficiaryTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BeneficiaryTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BeneficiaryType(123);
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
        const entity = new BeneficiaryType();
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
