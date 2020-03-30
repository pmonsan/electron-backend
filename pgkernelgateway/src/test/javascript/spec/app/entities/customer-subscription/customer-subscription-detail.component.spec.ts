/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { CustomerSubscriptionDetailComponent } from 'app/entities/customer-subscription/customer-subscription-detail.component';
import { CustomerSubscription } from 'app/shared/model/customer-subscription.model';

describe('Component Tests', () => {
  describe('CustomerSubscription Management Detail Component', () => {
    let comp: CustomerSubscriptionDetailComponent;
    let fixture: ComponentFixture<CustomerSubscriptionDetailComponent>;
    const route = ({ data: of({ customerSubscription: new CustomerSubscription(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [CustomerSubscriptionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CustomerSubscriptionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomerSubscriptionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.customerSubscription).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
