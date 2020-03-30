/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PartnerTypeComponent } from 'app/entities/partner-type/partner-type.component';
import { PartnerTypeService } from 'app/entities/partner-type/partner-type.service';
import { PartnerType } from 'app/shared/model/partner-type.model';

describe('Component Tests', () => {
  describe('PartnerType Management Component', () => {
    let comp: PartnerTypeComponent;
    let fixture: ComponentFixture<PartnerTypeComponent>;
    let service: PartnerTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PartnerTypeComponent],
        providers: []
      })
        .overrideTemplate(PartnerTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartnerTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartnerTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PartnerType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.partnerTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
