/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionPropertyComponent } from 'app/entities/transaction-property/transaction-property.component';
import { TransactionPropertyService } from 'app/entities/transaction-property/transaction-property.service';
import { TransactionProperty } from 'app/shared/model/transaction-property.model';

describe('Component Tests', () => {
  describe('TransactionProperty Management Component', () => {
    let comp: TransactionPropertyComponent;
    let fixture: ComponentFixture<TransactionPropertyComponent>;
    let service: TransactionPropertyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionPropertyComponent],
        providers: []
      })
        .overrideTemplate(TransactionPropertyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionPropertyComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionPropertyService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TransactionProperty(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.transactionProperties[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
