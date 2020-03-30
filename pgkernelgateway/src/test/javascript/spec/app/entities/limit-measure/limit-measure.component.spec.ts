/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { LimitMeasureComponent } from 'app/entities/limit-measure/limit-measure.component';
import { LimitMeasureService } from 'app/entities/limit-measure/limit-measure.service';
import { LimitMeasure } from 'app/shared/model/limit-measure.model';

describe('Component Tests', () => {
  describe('LimitMeasure Management Component', () => {
    let comp: LimitMeasureComponent;
    let fixture: ComponentFixture<LimitMeasureComponent>;
    let service: LimitMeasureService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [LimitMeasureComponent],
        providers: []
      })
        .overrideTemplate(LimitMeasureComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LimitMeasureComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LimitMeasureService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LimitMeasure(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.limitMeasures[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
