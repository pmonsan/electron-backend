/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PricePlanComponent } from 'app/entities/price-plan/price-plan.component';
import { PricePlanService } from 'app/entities/price-plan/price-plan.service';
import { PricePlan } from 'app/shared/model/price-plan.model';

describe('Component Tests', () => {
  describe('PricePlan Management Component', () => {
    let comp: PricePlanComponent;
    let fixture: ComponentFixture<PricePlanComponent>;
    let service: PricePlanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PricePlanComponent],
        providers: []
      })
        .overrideTemplate(PricePlanComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PricePlanComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PricePlanService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PricePlan(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pricePlans[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
