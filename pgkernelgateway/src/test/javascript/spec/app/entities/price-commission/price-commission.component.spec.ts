/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PriceCommissionComponent } from 'app/entities/price-commission/price-commission.component';
import { PriceCommissionService } from 'app/entities/price-commission/price-commission.service';
import { PriceCommission } from 'app/shared/model/price-commission.model';

describe('Component Tests', () => {
  describe('PriceCommission Management Component', () => {
    let comp: PriceCommissionComponent;
    let fixture: ComponentFixture<PriceCommissionComponent>;
    let service: PriceCommissionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PriceCommissionComponent],
        providers: []
      })
        .overrideTemplate(PriceCommissionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PriceCommissionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PriceCommissionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PriceCommission(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.priceCommissions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
