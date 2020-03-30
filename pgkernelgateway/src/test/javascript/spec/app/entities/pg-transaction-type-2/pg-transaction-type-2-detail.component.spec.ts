/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgTransactionType2DetailComponent } from 'app/entities/pg-transaction-type-2/pg-transaction-type-2-detail.component';
import { PgTransactionType2 } from 'app/shared/model/pg-transaction-type-2.model';

describe('Component Tests', () => {
  describe('PgTransactionType2 Management Detail Component', () => {
    let comp: PgTransactionType2DetailComponent;
    let fixture: ComponentFixture<PgTransactionType2DetailComponent>;
    const route = ({ data: of({ pgTransactionType2: new PgTransactionType2(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgTransactionType2DetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PgTransactionType2DetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgTransactionType2DetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pgTransactionType2).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
