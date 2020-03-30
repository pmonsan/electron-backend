/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionPropertyDetailComponent } from 'app/entities/transaction-property/transaction-property-detail.component';
import { TransactionProperty } from 'app/shared/model/transaction-property.model';

describe('Component Tests', () => {
  describe('TransactionProperty Management Detail Component', () => {
    let comp: TransactionPropertyDetailComponent;
    let fixture: ComponentFixture<TransactionPropertyDetailComponent>;
    const route = ({ data: of({ transactionProperty: new TransactionProperty(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionPropertyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TransactionPropertyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionPropertyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transactionProperty).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
