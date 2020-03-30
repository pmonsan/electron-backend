/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ServiceIntegrationComponent } from 'app/entities/service-integration/service-integration.component';
import { ServiceIntegrationService } from 'app/entities/service-integration/service-integration.service';
import { ServiceIntegration } from 'app/shared/model/service-integration.model';

describe('Component Tests', () => {
  describe('ServiceIntegration Management Component', () => {
    let comp: ServiceIntegrationComponent;
    let fixture: ComponentFixture<ServiceIntegrationComponent>;
    let service: ServiceIntegrationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ServiceIntegrationComponent],
        providers: []
      })
        .overrideTemplate(ServiceIntegrationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceIntegrationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceIntegrationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ServiceIntegration(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.serviceIntegrations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
