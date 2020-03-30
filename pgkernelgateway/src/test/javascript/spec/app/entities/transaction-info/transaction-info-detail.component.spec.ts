/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionInfoDetailComponent } from 'app/entities/transaction-info/transaction-info-detail.component';
import { TransactionInfo } from 'app/shared/model/transaction-info.model';

describe('Component Tests', () => {
  describe('TransactionInfo Management Detail Component', () => {
    let comp: TransactionInfoDetailComponent;
    let fixture: ComponentFixture<TransactionInfoDetailComponent>;
    const route = ({ data: of({ transactionInfo: new TransactionInfo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionInfoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TransactionInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transactionInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
