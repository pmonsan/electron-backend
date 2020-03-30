/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionPriceComponent } from 'app/entities/transaction-price/transaction-price.component';
import { TransactionPriceService } from 'app/entities/transaction-price/transaction-price.service';
import { TransactionPrice } from 'app/shared/model/transaction-price.model';

describe('Component Tests', () => {
  describe('TransactionPrice Management Component', () => {
    let comp: TransactionPriceComponent;
    let fixture: ComponentFixture<TransactionPriceComponent>;
    let service: TransactionPriceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionPriceComponent],
        providers: []
      })
        .overrideTemplate(TransactionPriceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionPriceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionPriceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TransactionPrice(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.transactionPrices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
