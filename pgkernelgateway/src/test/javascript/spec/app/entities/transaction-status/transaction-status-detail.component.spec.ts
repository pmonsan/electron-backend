/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionStatusDetailComponent } from 'app/entities/transaction-status/transaction-status-detail.component';
import { TransactionStatus } from 'app/shared/model/transaction-status.model';

describe('Component Tests', () => {
  describe('TransactionStatus Management Detail Component', () => {
    let comp: TransactionStatusDetailComponent;
    let fixture: ComponentFixture<TransactionStatusDetailComponent>;
    const route = ({ data: of({ transactionStatus: new TransactionStatus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionStatusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TransactionStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transactionStatus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
