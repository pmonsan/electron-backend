/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { DetailTransactionDetailComponent } from 'app/entities/detail-transaction/detail-transaction-detail.component';
import { DetailTransaction } from 'app/shared/model/detail-transaction.model';

describe('Component Tests', () => {
  describe('DetailTransaction Management Detail Component', () => {
    let comp: DetailTransactionDetailComponent;
    let fixture: ComponentFixture<DetailTransactionDetailComponent>;
    const route = ({ data: of({ detailTransaction: new DetailTransaction(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [DetailTransactionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DetailTransactionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DetailTransactionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.detailTransaction).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
