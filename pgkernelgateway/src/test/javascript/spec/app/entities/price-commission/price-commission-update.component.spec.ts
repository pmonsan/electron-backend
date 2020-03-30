/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PriceCommissionUpdateComponent } from 'app/entities/price-commission/price-commission-update.component';
import { PriceCommissionService } from 'app/entities/price-commission/price-commission.service';
import { PriceCommission } from 'app/shared/model/price-commission.model';

describe('Component Tests', () => {
  describe('PriceCommission Management Update Component', () => {
    let comp: PriceCommissionUpdateComponent;
    let fixture: ComponentFixture<PriceCommissionUpdateComponent>;
    let service: PriceCommissionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PriceCommissionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PriceCommissionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PriceCommissionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PriceCommissionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PriceCommission(123);
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
        const entity = new PriceCommission();
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
