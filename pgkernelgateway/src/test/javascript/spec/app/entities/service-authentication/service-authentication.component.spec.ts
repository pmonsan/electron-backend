/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ServiceAuthenticationComponent } from 'app/entities/service-authentication/service-authentication.component';
import { ServiceAuthenticationService } from 'app/entities/service-authentication/service-authentication.service';
import { ServiceAuthentication } from 'app/shared/model/service-authentication.model';

describe('Component Tests', () => {
  describe('ServiceAuthentication Management Component', () => {
    let comp: ServiceAuthenticationComponent;
    let fixture: ComponentFixture<ServiceAuthenticationComponent>;
    let service: ServiceAuthenticationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ServiceAuthenticationComponent],
        providers: []
      })
        .overrideTemplate(ServiceAuthenticationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceAuthenticationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceAuthenticationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ServiceAuthentication(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.serviceAuthentications[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
