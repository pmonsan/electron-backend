/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionCommissionDetailComponent } from 'app/entities/transaction-commission/transaction-commission-detail.component';
import { TransactionCommission } from 'app/shared/model/transaction-commission.model';

describe('Component Tests', () => {
  describe('TransactionCommission Management Detail Component', () => {
    let comp: TransactionCommissionDetailComponent;
    let fixture: ComponentFixture<TransactionCommissionDetailComponent>;
    const route = ({ data: of({ transactionCommission: new TransactionCommission(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionCommissionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TransactionCommissionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionCommissionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transactionCommission).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
