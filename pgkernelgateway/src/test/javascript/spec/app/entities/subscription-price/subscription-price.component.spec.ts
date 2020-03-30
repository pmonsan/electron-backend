/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { SubscriptionPriceComponent } from 'app/entities/subscription-price/subscription-price.component';
import { SubscriptionPriceService } from 'app/entities/subscription-price/subscription-price.service';
import { SubscriptionPrice } from 'app/shared/model/subscription-price.model';

describe('Component Tests', () => {
  describe('SubscriptionPrice Management Component', () => {
    let comp: SubscriptionPriceComponent;
    let fixture: ComponentFixture<SubscriptionPriceComponent>;
    let service: SubscriptionPriceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [SubscriptionPriceComponent],
        providers: []
      })
        .overrideTemplate(SubscriptionPriceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubscriptionPriceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubscriptionPriceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SubscriptionPrice(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.subscriptionPrices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
