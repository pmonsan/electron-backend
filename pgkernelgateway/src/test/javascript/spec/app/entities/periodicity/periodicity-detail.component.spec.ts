/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PeriodicityDetailComponent } from 'app/entities/periodicity/periodicity-detail.component';
import { Periodicity } from 'app/shared/model/periodicity.model';

describe('Component Tests', () => {
  describe('Periodicity Management Detail Component', () => {
    let comp: PeriodicityDetailComponent;
    let fixture: ComponentFixture<PeriodicityDetailComponent>;
    const route = ({ data: of({ periodicity: new Periodicity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PeriodicityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PeriodicityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PeriodicityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.periodicity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
