/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionPriceDetailComponent } from 'app/entities/transaction-price/transaction-price-detail.component';
import { TransactionPrice } from 'app/shared/model/transaction-price.model';

describe('Component Tests', () => {
  describe('TransactionPrice Management Detail Component', () => {
    let comp: TransactionPriceDetailComponent;
    let fixture: ComponentFixture<TransactionPriceDetailComponent>;
    const route = ({ data: of({ transactionPrice: new TransactionPrice(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionPriceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TransactionPriceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionPriceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transactionPrice).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
