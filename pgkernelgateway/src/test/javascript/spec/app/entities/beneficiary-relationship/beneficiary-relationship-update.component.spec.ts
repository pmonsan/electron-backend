/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { BeneficiaryRelationshipUpdateComponent } from 'app/entities/beneficiary-relationship/beneficiary-relationship-update.component';
import { BeneficiaryRelationshipService } from 'app/entities/beneficiary-relationship/beneficiary-relationship.service';
import { BeneficiaryRelationship } from 'app/shared/model/beneficiary-relationship.model';

describe('Component Tests', () => {
  describe('BeneficiaryRelationship Management Update Component', () => {
    let comp: BeneficiaryRelationshipUpdateComponent;
    let fixture: ComponentFixture<BeneficiaryRelationshipUpdateComponent>;
    let service: BeneficiaryRelationshipService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [BeneficiaryRelationshipUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BeneficiaryRelationshipUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BeneficiaryRelationshipUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BeneficiaryRelationshipService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BeneficiaryRelationship(123);
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
        const entity = new BeneficiaryRelationship();
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
