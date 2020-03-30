/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PriceComponent } from 'app/entities/price/price.component';
import { PriceService } from 'app/entities/price/price.service';
import { Price } from 'app/shared/model/price.model';

describe('Component Tests', () => {
  describe('Price Management Component', () => {
    let comp: PriceComponent;
    let fixture: ComponentFixture<PriceComponent>;
    let service: PriceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PriceComponent],
        providers: []
      })
        .overrideTemplate(PriceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PriceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PriceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Price(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.prices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
