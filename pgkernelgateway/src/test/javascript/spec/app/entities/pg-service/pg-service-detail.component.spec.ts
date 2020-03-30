/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgServiceDetailComponent } from 'app/entities/pg-service/pg-service-detail.component';
import { PgService } from 'app/shared/model/pg-service.model';

describe('Component Tests', () => {
  describe('PgService Management Detail Component', () => {
    let comp: PgServiceDetailComponent;
    let fixture: ComponentFixture<PgServiceDetailComponent>;
    const route = ({ data: of({ pgService: new PgService(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgServiceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PgServiceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgServiceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pgService).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
