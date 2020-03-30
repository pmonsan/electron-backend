/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { CustomerSubscriptionComponent } from 'app/entities/customer-subscription/customer-subscription.component';
import { CustomerSubscriptionService } from 'app/entities/customer-subscription/customer-subscription.service';
import { CustomerSubscription } from 'app/shared/model/customer-subscription.model';

describe('Component Tests', () => {
  describe('CustomerSubscription Management Component', () => {
    let comp: CustomerSubscriptionComponent;
    let fixture: ComponentFixture<CustomerSubscriptionComponent>;
    let service: CustomerSubscriptionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [CustomerSubscriptionComponent],
        providers: []
      })
        .overrideTemplate(CustomerSubscriptionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerSubscriptionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerSubscriptionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CustomerSubscription(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.customerSubscriptions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
