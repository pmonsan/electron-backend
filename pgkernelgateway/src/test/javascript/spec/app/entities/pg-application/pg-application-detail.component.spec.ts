/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgApplicationDetailComponent } from 'app/entities/pg-application/pg-application-detail.component';
import { PgApplication } from 'app/shared/model/pg-application.model';

describe('Component Tests', () => {
  describe('PgApplication Management Detail Component', () => {
    let comp: PgApplicationDetailComponent;
    let fixture: ComponentFixture<PgApplicationDetailComponent>;
    const route = ({ data: of({ pgApplication: new PgApplication(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgApplicationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PgApplicationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgApplicationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pgApplication).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
