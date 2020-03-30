/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgMessageModelDataDetailComponent } from 'app/entities/pg-message-model-data/pg-message-model-data-detail.component';
import { PgMessageModelData } from 'app/shared/model/pg-message-model-data.model';

describe('Component Tests', () => {
  describe('PgMessageModelData Management Detail Component', () => {
    let comp: PgMessageModelDataDetailComponent;
    let fixture: ComponentFixture<PgMessageModelDataDetailComponent>;
    const route = ({ data: of({ pgMessageModelData: new PgMessageModelData(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgMessageModelDataDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PgMessageModelDataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgMessageModelDataDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pgMessageModelData).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
