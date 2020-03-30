/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PricePlanDetailComponent } from 'app/entities/price-plan/price-plan-detail.component';
import { PricePlan } from 'app/shared/model/price-plan.model';

describe('Component Tests', () => {
  describe('PricePlan Management Detail Component', () => {
    let comp: PricePlanDetailComponent;
    let fixture: ComponentFixture<PricePlanDetailComponent>;
    const route = ({ data: of({ pricePlan: new PricePlan(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PricePlanDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PricePlanDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PricePlanDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pricePlan).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
