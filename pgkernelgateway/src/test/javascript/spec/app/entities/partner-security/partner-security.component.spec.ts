/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PartnerSecurityComponent } from 'app/entities/partner-security/partner-security.component';
import { PartnerSecurityService } from 'app/entities/partner-security/partner-security.service';
import { PartnerSecurity } from 'app/shared/model/partner-security.model';

describe('Component Tests', () => {
  describe('PartnerSecurity Management Component', () => {
    let comp: PartnerSecurityComponent;
    let fixture: ComponentFixture<PartnerSecurityComponent>;
    let service: PartnerSecurityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PartnerSecurityComponent],
        providers: []
      })
        .overrideTemplate(PartnerSecurityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartnerSecurityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartnerSecurityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PartnerSecurity(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.partnerSecurities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
