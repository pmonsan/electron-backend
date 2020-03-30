/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionCommissionComponent } from 'app/entities/transaction-commission/transaction-commission.component';
import { TransactionCommissionService } from 'app/entities/transaction-commission/transaction-commission.service';
import { TransactionCommission } from 'app/shared/model/transaction-commission.model';

describe('Component Tests', () => {
  describe('TransactionCommission Management Component', () => {
    let comp: TransactionCommissionComponent;
    let fixture: ComponentFixture<TransactionCommissionComponent>;
    let service: TransactionCommissionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionCommissionComponent],
        providers: []
      })
        .overrideTemplate(TransactionCommissionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionCommissionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionCommissionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TransactionCommission(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.transactionCommissions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
