/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionGroupDetailComponent } from 'app/entities/transaction-group/transaction-group-detail.component';
import { TransactionGroup } from 'app/shared/model/transaction-group.model';

describe('Component Tests', () => {
  describe('TransactionGroup Management Detail Component', () => {
    let comp: TransactionGroupDetailComponent;
    let fixture: ComponentFixture<TransactionGroupDetailComponent>;
    const route = ({ data: of({ transactionGroup: new TransactionGroup(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionGroupDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TransactionGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionGroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transactionGroup).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
