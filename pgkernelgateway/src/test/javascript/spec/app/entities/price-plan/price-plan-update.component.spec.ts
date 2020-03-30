/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PricePlanUpdateComponent } from 'app/entities/price-plan/price-plan-update.component';
import { PricePlanService } from 'app/entities/price-plan/price-plan.service';
import { PricePlan } from 'app/shared/model/price-plan.model';

describe('Component Tests', () => {
  describe('PricePlan Management Update Component', () => {
    let comp: PricePlanUpdateComponent;
    let fixture: ComponentFixture<PricePlanUpdateComponent>;
    let service: PricePlanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PricePlanUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PricePlanUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PricePlanUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PricePlanService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PricePlan(123);
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
        const entity = new PricePlan();
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
