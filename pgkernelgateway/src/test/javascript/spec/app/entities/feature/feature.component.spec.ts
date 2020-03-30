/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { FeatureComponent } from 'app/entities/feature/feature.component';
import { FeatureService } from 'app/entities/feature/feature.service';
import { Feature } from 'app/shared/model/feature.model';

describe('Component Tests', () => {
  describe('Feature Management Component', () => {
    let comp: FeatureComponent;
    let fixture: ComponentFixture<FeatureComponent>;
    let service: FeatureService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [FeatureComponent],
        providers: []
      })
        .overrideTemplate(FeatureComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FeatureComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FeatureService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Feature(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.features[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
