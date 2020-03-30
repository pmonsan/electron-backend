/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgApplicationServiceDetailComponent } from 'app/entities/pg-application-service/pg-application-service-detail.component';
import { PgApplicationService } from 'app/shared/model/pg-application-service.model';

describe('Component Tests', () => {
  describe('PgApplicationService Management Detail Component', () => {
    let comp: PgApplicationServiceDetailComponent;
    let fixture: ComponentFixture<PgApplicationServiceDetailComponent>;
    const route = ({ data: of({ pgApplicationService: new PgApplicationService(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgApplicationServiceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PgApplicationServiceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgApplicationServiceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pgApplicationService).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
