/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PeriodicityComponent } from 'app/entities/periodicity/periodicity.component';
import { PeriodicityService } from 'app/entities/periodicity/periodicity.service';
import { Periodicity } from 'app/shared/model/periodicity.model';

describe('Component Tests', () => {
  describe('Periodicity Management Component', () => {
    let comp: PeriodicityComponent;
    let fixture: ComponentFixture<PeriodicityComponent>;
    let service: PeriodicityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PeriodicityComponent],
        providers: []
      })
        .overrideTemplate(PeriodicityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PeriodicityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PeriodicityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Periodicity(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.periodicities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
