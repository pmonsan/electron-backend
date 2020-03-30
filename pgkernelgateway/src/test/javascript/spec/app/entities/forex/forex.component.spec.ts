/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ForexComponent } from 'app/entities/forex/forex.component';
import { ForexService } from 'app/entities/forex/forex.service';
import { Forex } from 'app/shared/model/forex.model';

describe('Component Tests', () => {
  describe('Forex Management Component', () => {
    let comp: ForexComponent;
    let fixture: ComponentFixture<ForexComponent>;
    let service: ForexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ForexComponent],
        providers: []
      })
        .overrideTemplate(ForexComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ForexComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ForexService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Forex(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.forexes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
