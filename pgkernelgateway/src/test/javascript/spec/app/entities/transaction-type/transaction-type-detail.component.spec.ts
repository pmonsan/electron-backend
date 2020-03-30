/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionTypeDetailComponent } from 'app/entities/transaction-type/transaction-type-detail.component';
import { TransactionType } from 'app/shared/model/transaction-type.model';

describe('Component Tests', () => {
  describe('TransactionType Management Detail Component', () => {
    let comp: TransactionTypeDetailComponent;
    let fixture: ComponentFixture<TransactionTypeDetailComponent>;
    const route = ({ data: of({ transactionType: new TransactionType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TransactionTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transactionType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
