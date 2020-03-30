/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { LimitMeasureDetailComponent } from 'app/entities/limit-measure/limit-measure-detail.component';
import { LimitMeasure } from 'app/shared/model/limit-measure.model';

describe('Component Tests', () => {
  describe('LimitMeasure Management Detail Component', () => {
    let comp: LimitMeasureDetailComponent;
    let fixture: ComponentFixture<LimitMeasureDetailComponent>;
    const route = ({ data: of({ limitMeasure: new LimitMeasure(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [LimitMeasureDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LimitMeasureDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LimitMeasureDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.limitMeasure).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
