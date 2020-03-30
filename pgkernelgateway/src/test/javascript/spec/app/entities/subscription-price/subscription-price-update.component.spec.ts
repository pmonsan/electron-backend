/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { SubscriptionPriceUpdateComponent } from 'app/entities/subscription-price/subscription-price-update.component';
import { SubscriptionPriceService } from 'app/entities/subscription-price/subscription-price.service';
import { SubscriptionPrice } from 'app/shared/model/subscription-price.model';

describe('Component Tests', () => {
  describe('SubscriptionPrice Management Update Component', () => {
    let comp: SubscriptionPriceUpdateComponent;
    let fixture: ComponentFixture<SubscriptionPriceUpdateComponent>;
    let service: SubscriptionPriceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [SubscriptionPriceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SubscriptionPriceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubscriptionPriceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubscriptionPriceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SubscriptionPrice(123);
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
        const entity = new SubscriptionPrice();
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
