/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgBatchDetailComponent } from 'app/entities/pg-batch/pg-batch-detail.component';
import { PgBatch } from 'app/shared/model/pg-batch.model';

describe('Component Tests', () => {
  describe('PgBatch Management Detail Component', () => {
    let comp: PgBatchDetailComponent;
    let fixture: ComponentFixture<PgBatchDetailComponent>;
    const route = ({ data: of({ pgBatch: new PgBatch(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgBatchDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PgBatchDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgBatchDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pgBatch).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
