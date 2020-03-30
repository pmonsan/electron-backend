/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { Pg8583CallbackComponent } from 'app/entities/pg-8583-callback/pg-8583-callback.component';
import { Pg8583CallbackService } from 'app/entities/pg-8583-callback/pg-8583-callback.service';
import { Pg8583Callback } from 'app/shared/model/pg-8583-callback.model';

describe('Component Tests', () => {
  describe('Pg8583Callback Management Component', () => {
    let comp: Pg8583CallbackComponent;
    let fixture: ComponentFixture<Pg8583CallbackComponent>;
    let service: Pg8583CallbackService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [Pg8583CallbackComponent],
        providers: []
      })
        .overrideTemplate(Pg8583CallbackComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Pg8583CallbackComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Pg8583CallbackService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Pg8583Callback(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pg8583Callbacks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
