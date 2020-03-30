/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionGroupComponent } from 'app/entities/transaction-group/transaction-group.component';
import { TransactionGroupService } from 'app/entities/transaction-group/transaction-group.service';
import { TransactionGroup } from 'app/shared/model/transaction-group.model';

describe('Component Tests', () => {
  describe('TransactionGroup Management Component', () => {
    let comp: TransactionGroupComponent;
    let fixture: ComponentFixture<TransactionGroupComponent>;
    let service: TransactionGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionGroupComponent],
        providers: []
      })
        .overrideTemplate(TransactionGroupComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionGroupComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionGroupService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TransactionGroup(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.transactionGroups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
