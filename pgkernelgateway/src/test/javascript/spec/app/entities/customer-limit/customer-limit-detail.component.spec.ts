/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { CustomerLimitDetailComponent } from 'app/entities/customer-limit/customer-limit-detail.component';
import { CustomerLimit } from 'app/shared/model/customer-limit.model';

describe('Component Tests', () => {
  describe('CustomerLimit Management Detail Component', () => {
    let comp: CustomerLimitDetailComponent;
    let fixture: ComponentFixture<CustomerLimitDetailComponent>;
    const route = ({ data: of({ customerLimit: new CustomerLimit(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [CustomerLimitDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CustomerLimitDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomerLimitDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.customerLimit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
