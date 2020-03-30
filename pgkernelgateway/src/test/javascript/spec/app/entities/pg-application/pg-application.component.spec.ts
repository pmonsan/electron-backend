/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgApplicationComponent } from 'app/entities/pg-application/pg-application.component';
import { PgApplicationService } from 'app/entities/pg-application/pg-application.service';
import { PgApplication } from 'app/shared/model/pg-application.model';

describe('Component Tests', () => {
  describe('PgApplication Management Component', () => {
    let comp: PgApplicationComponent;
    let fixture: ComponentFixture<PgApplicationComponent>;
    let service: PgApplicationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgApplicationComponent],
        providers: []
      })
        .overrideTemplate(PgApplicationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgApplicationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgApplicationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PgApplication(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pgApplications[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
