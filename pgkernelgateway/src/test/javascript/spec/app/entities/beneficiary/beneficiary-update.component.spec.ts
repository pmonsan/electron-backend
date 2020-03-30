/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { BeneficiaryUpdateComponent } from 'app/entities/beneficiary/beneficiary-update.component';
import { BeneficiaryService } from 'app/entities/beneficiary/beneficiary.service';
import { Beneficiary } from 'app/shared/model/beneficiary.model';

describe('Component Tests', () => {
  describe('Beneficiary Management Update Component', () => {
    let comp: BeneficiaryUpdateComponent;
    let fixture: ComponentFixture<BeneficiaryUpdateComponent>;
    let service: BeneficiaryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [BeneficiaryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BeneficiaryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BeneficiaryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BeneficiaryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Beneficiary(123);
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
        const entity = new Beneficiary();
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
