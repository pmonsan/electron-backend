/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { Pg8583RequestComponent } from 'app/entities/pg-8583-request/pg-8583-request.component';
import { Pg8583RequestService } from 'app/entities/pg-8583-request/pg-8583-request.service';
import { Pg8583Request } from 'app/shared/model/pg-8583-request.model';

describe('Component Tests', () => {
  describe('Pg8583Request Management Component', () => {
    let comp: Pg8583RequestComponent;
    let fixture: ComponentFixture<Pg8583RequestComponent>;
    let service: Pg8583RequestService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [Pg8583RequestComponent],
        providers: []
      })
        .overrideTemplate(Pg8583RequestComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Pg8583RequestComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Pg8583RequestService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Pg8583Request(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pg8583Requests[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
