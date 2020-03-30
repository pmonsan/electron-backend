/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PriceUpdateComponent } from 'app/entities/price/price-update.component';
import { PriceService } from 'app/entities/price/price.service';
import { Price } from 'app/shared/model/price.model';

describe('Component Tests', () => {
  describe('Price Management Update Component', () => {
    let comp: PriceUpdateComponent;
    let fixture: ComponentFixture<PriceUpdateComponent>;
    let service: PriceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PriceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PriceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PriceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PriceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Price(123);
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
        const entity = new Price();
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
