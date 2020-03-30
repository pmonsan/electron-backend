/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgTransactionType1DetailComponent } from 'app/entities/pg-transaction-type-1/pg-transaction-type-1-detail.component';
import { PgTransactionType1 } from 'app/shared/model/pg-transaction-type-1.model';

describe('Component Tests', () => {
  describe('PgTransactionType1 Management Detail Component', () => {
    let comp: PgTransactionType1DetailComponent;
    let fixture: ComponentFixture<PgTransactionType1DetailComponent>;
    const route = ({ data: of({ pgTransactionType1: new PgTransactionType1(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgTransactionType1DetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PgTransactionType1DetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgTransactionType1DetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pgTransactionType1).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
