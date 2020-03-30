/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { CustomerLimitComponent } from 'app/entities/customer-limit/customer-limit.component';
import { CustomerLimitService } from 'app/entities/customer-limit/customer-limit.service';
import { CustomerLimit } from 'app/shared/model/customer-limit.model';

describe('Component Tests', () => {
  describe('CustomerLimit Management Component', () => {
    let comp: CustomerLimitComponent;
    let fixture: ComponentFixture<CustomerLimitComponent>;
    let service: CustomerLimitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [CustomerLimitComponent],
        providers: []
      })
        .overrideTemplate(CustomerLimitComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerLimitComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerLimitService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CustomerLimit(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.customerLimits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
