/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { Pg8583StatusComponent } from 'app/entities/pg-8583-status/pg-8583-status.component';
import { Pg8583StatusService } from 'app/entities/pg-8583-status/pg-8583-status.service';
import { Pg8583Status } from 'app/shared/model/pg-8583-status.model';

describe('Component Tests', () => {
  describe('Pg8583Status Management Component', () => {
    let comp: Pg8583StatusComponent;
    let fixture: ComponentFixture<Pg8583StatusComponent>;
    let service: Pg8583StatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [Pg8583StatusComponent],
        providers: []
      })
        .overrideTemplate(Pg8583StatusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Pg8583StatusComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Pg8583StatusService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Pg8583Status(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pg8583Statuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
