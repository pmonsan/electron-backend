/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionInfoComponent } from 'app/entities/transaction-info/transaction-info.component';
import { TransactionInfoService } from 'app/entities/transaction-info/transaction-info.service';
import { TransactionInfo } from 'app/shared/model/transaction-info.model';

describe('Component Tests', () => {
  describe('TransactionInfo Management Component', () => {
    let comp: TransactionInfoComponent;
    let fixture: ComponentFixture<TransactionInfoComponent>;
    let service: TransactionInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionInfoComponent],
        providers: []
      })
        .overrideTemplate(TransactionInfoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionInfoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionInfoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TransactionInfo(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.transactionInfos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
