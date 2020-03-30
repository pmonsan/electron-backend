/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { CurrencyComponent } from 'app/entities/currency/currency.component';
import { CurrencyService } from 'app/entities/currency/currency.service';
import { Currency } from 'app/shared/model/currency.model';

describe('Component Tests', () => {
  describe('Currency Management Component', () => {
    let comp: CurrencyComponent;
    let fixture: ComponentFixture<CurrencyComponent>;
    let service: CurrencyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [CurrencyComponent],
        providers: []
      })
        .overrideTemplate(CurrencyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CurrencyComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CurrencyService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Currency(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.currencies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
