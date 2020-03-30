/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgApplicationServiceComponent } from 'app/entities/pg-application-service/pg-application-service.component';
import { PgApplicationServiceService } from 'app/entities/pg-application-service/pg-application-service.service';
import { PgApplicationService } from 'app/shared/model/pg-application-service.model';

describe('Component Tests', () => {
  describe('PgApplicationService Management Component', () => {
    let comp: PgApplicationServiceComponent;
    let fixture: ComponentFixture<PgApplicationServiceComponent>;
    let service: PgApplicationServiceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgApplicationServiceComponent],
        providers: []
      })
        .overrideTemplate(PgApplicationServiceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgApplicationServiceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgApplicationServiceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PgApplicationService(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pgApplicationServices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
