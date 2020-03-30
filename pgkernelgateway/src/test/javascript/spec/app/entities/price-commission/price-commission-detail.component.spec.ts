/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PriceCommissionDetailComponent } from 'app/entities/price-commission/price-commission-detail.component';
import { PriceCommission } from 'app/shared/model/price-commission.model';

describe('Component Tests', () => {
  describe('PriceCommission Management Detail Component', () => {
    let comp: PriceCommissionDetailComponent;
    let fixture: ComponentFixture<PriceCommissionDetailComponent>;
    const route = ({ data: of({ priceCommission: new PriceCommission(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PriceCommissionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PriceCommissionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PriceCommissionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.priceCommission).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
