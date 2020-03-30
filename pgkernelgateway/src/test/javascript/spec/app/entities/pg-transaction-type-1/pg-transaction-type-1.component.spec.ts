/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgTransactionType1Component } from 'app/entities/pg-transaction-type-1/pg-transaction-type-1.component';
import { PgTransactionType1Service } from 'app/entities/pg-transaction-type-1/pg-transaction-type-1.service';
import { PgTransactionType1 } from 'app/shared/model/pg-transaction-type-1.model';

describe('Component Tests', () => {
  describe('PgTransactionType1 Management Component', () => {
    let comp: PgTransactionType1Component;
    let fixture: ComponentFixture<PgTransactionType1Component>;
    let service: PgTransactionType1Service;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgTransactionType1Component],
        providers: []
      })
        .overrideTemplate(PgTransactionType1Component, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgTransactionType1Component);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgTransactionType1Service);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PgTransactionType1(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pgTransactionType1S[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
