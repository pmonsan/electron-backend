/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ServiceLimitComponent } from 'app/entities/service-limit/service-limit.component';
import { ServiceLimitService } from 'app/entities/service-limit/service-limit.service';
import { ServiceLimit } from 'app/shared/model/service-limit.model';

describe('Component Tests', () => {
  describe('ServiceLimit Management Component', () => {
    let comp: ServiceLimitComponent;
    let fixture: ComponentFixture<ServiceLimitComponent>;
    let service: ServiceLimitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ServiceLimitComponent],
        providers: []
      })
        .overrideTemplate(ServiceLimitComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceLimitComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceLimitService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ServiceLimit(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.serviceLimits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
