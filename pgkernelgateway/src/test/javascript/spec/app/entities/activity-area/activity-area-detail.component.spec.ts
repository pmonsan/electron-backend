/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ActivityAreaDetailComponent } from 'app/entities/activity-area/activity-area-detail.component';
import { ActivityArea } from 'app/shared/model/activity-area.model';

describe('Component Tests', () => {
  describe('ActivityArea Management Detail Component', () => {
    let comp: ActivityAreaDetailComponent;
    let fixture: ComponentFixture<ActivityAreaDetailComponent>;
    const route = ({ data: of({ activityArea: new ActivityArea(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ActivityAreaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ActivityAreaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ActivityAreaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.activityArea).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
