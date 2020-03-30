/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { CustomerSubscriptionUpdateComponent } from 'app/entities/customer-subscription/customer-subscription-update.component';
import { CustomerSubscriptionService } from 'app/entities/customer-subscription/customer-subscription.service';
import { CustomerSubscription } from 'app/shared/model/customer-subscription.model';

describe('Component Tests', () => {
  describe('CustomerSubscription Management Update Component', () => {
    let comp: CustomerSubscriptionUpdateComponent;
    let fixture: ComponentFixture<CustomerSubscriptionUpdateComponent>;
    let service: CustomerSubscriptionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [CustomerSubscriptionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CustomerSubscriptionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerSubscriptionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerSubscriptionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CustomerSubscription(123);
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
        const entity = new CustomerSubscription();
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
