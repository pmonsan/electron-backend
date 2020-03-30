/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgTransactionType2Component } from 'app/entities/pg-transaction-type-2/pg-transaction-type-2.component';
import { PgTransactionType2Service } from 'app/entities/pg-transaction-type-2/pg-transaction-type-2.service';
import { PgTransactionType2 } from 'app/shared/model/pg-transaction-type-2.model';

describe('Component Tests', () => {
  describe('PgTransactionType2 Management Component', () => {
    let comp: PgTransactionType2Component;
    let fixture: ComponentFixture<PgTransactionType2Component>;
    let service: PgTransactionType2Service;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgTransactionType2Component],
        providers: []
      })
        .overrideTemplate(PgTransactionType2Component, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgTransactionType2Component);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgTransactionType2Service);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PgTransactionType2(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pgTransactionType2S[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
