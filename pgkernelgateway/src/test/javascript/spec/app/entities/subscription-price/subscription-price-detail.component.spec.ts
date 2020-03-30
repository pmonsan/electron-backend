/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { SubscriptionPriceDetailComponent } from 'app/entities/subscription-price/subscription-price-detail.component';
import { SubscriptionPrice } from 'app/shared/model/subscription-price.model';

describe('Component Tests', () => {
  describe('SubscriptionPrice Management Detail Component', () => {
    let comp: SubscriptionPriceDetailComponent;
    let fixture: ComponentFixture<SubscriptionPriceDetailComponent>;
    const route = ({ data: of({ subscriptionPrice: new SubscriptionPrice(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [SubscriptionPriceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SubscriptionPriceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SubscriptionPriceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.subscriptionPrice).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
