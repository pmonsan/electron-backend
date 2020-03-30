/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { CustomerTypeComponent } from 'app/entities/customer-type/customer-type.component';
import { CustomerTypeService } from 'app/entities/customer-type/customer-type.service';
import { CustomerType } from 'app/shared/model/customer-type.model';

describe('Component Tests', () => {
  describe('CustomerType Management Component', () => {
    let comp: CustomerTypeComponent;
    let fixture: ComponentFixture<CustomerTypeComponent>;
    let service: CustomerTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [CustomerTypeComponent],
        providers: []
      })
        .overrideTemplate(CustomerTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CustomerType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.customerTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
