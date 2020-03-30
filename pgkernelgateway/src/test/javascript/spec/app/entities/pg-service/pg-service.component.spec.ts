/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgServiceComponent } from 'app/entities/pg-service/pg-service.component';
import { PgServiceService } from 'app/entities/pg-service/pg-service.service';
import { PgService } from 'app/shared/model/pg-service.model';

describe('Component Tests', () => {
  describe('PgService Management Component', () => {
    let comp: PgServiceComponent;
    let fixture: ComponentFixture<PgServiceComponent>;
    let service: PgServiceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgServiceComponent],
        providers: []
      })
        .overrideTemplate(PgServiceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgServiceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgServiceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PgService(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pgServices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
