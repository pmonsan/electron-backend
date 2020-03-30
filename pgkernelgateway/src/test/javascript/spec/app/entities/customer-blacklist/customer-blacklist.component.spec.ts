/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { CustomerBlacklistComponent } from 'app/entities/customer-blacklist/customer-blacklist.component';
import { CustomerBlacklistService } from 'app/entities/customer-blacklist/customer-blacklist.service';
import { CustomerBlacklist } from 'app/shared/model/customer-blacklist.model';

describe('Component Tests', () => {
  describe('CustomerBlacklist Management Component', () => {
    let comp: CustomerBlacklistComponent;
    let fixture: ComponentFixture<CustomerBlacklistComponent>;
    let service: CustomerBlacklistService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [CustomerBlacklistComponent],
        providers: []
      })
        .overrideTemplate(CustomerBlacklistComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerBlacklistComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerBlacklistService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CustomerBlacklist(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.customerBlacklists[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
