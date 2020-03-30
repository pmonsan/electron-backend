/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgDataDetailComponent } from 'app/entities/pg-data/pg-data-detail.component';
import { PgData } from 'app/shared/model/pg-data.model';

describe('Component Tests', () => {
  describe('PgData Management Detail Component', () => {
    let comp: PgDataDetailComponent;
    let fixture: ComponentFixture<PgDataDetailComponent>;
    const route = ({ data: of({ pgData: new PgData(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgDataDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PgDataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgDataDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pgData).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
