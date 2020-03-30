/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionStatusComponent } from 'app/entities/transaction-status/transaction-status.component';
import { TransactionStatusService } from 'app/entities/transaction-status/transaction-status.service';
import { TransactionStatus } from 'app/shared/model/transaction-status.model';

describe('Component Tests', () => {
  describe('TransactionStatus Management Component', () => {
    let comp: TransactionStatusComponent;
    let fixture: ComponentFixture<TransactionStatusComponent>;
    let service: TransactionStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionStatusComponent],
        providers: []
      })
        .overrideTemplate(TransactionStatusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionStatusComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionStatusService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TransactionStatus(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.transactionStatuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
