/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { DetailTransactionComponent } from 'app/entities/detail-transaction/detail-transaction.component';
import { DetailTransactionService } from 'app/entities/detail-transaction/detail-transaction.service';
import { DetailTransaction } from 'app/shared/model/detail-transaction.model';

describe('Component Tests', () => {
  describe('DetailTransaction Management Component', () => {
    let comp: DetailTransactionComponent;
    let fixture: ComponentFixture<DetailTransactionComponent>;
    let service: DetailTransactionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [DetailTransactionComponent],
        providers: []
      })
        .overrideTemplate(DetailTransactionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DetailTransactionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DetailTransactionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DetailTransaction(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.detailTransactions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
