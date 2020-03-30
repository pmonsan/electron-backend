/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ActivityAreaComponent } from 'app/entities/activity-area/activity-area.component';
import { ActivityAreaService } from 'app/entities/activity-area/activity-area.service';
import { ActivityArea } from 'app/shared/model/activity-area.model';

describe('Component Tests', () => {
  describe('ActivityArea Management Component', () => {
    let comp: ActivityAreaComponent;
    let fixture: ComponentFixture<ActivityAreaComponent>;
    let service: ActivityAreaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ActivityAreaComponent],
        providers: []
      })
        .overrideTemplate(ActivityAreaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ActivityAreaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ActivityAreaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ActivityArea(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.activityAreas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
