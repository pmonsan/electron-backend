/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgMessageModelDetailComponent } from 'app/entities/pg-message-model/pg-message-model-detail.component';
import { PgMessageModel } from 'app/shared/model/pg-message-model.model';

describe('Component Tests', () => {
  describe('PgMessageModel Management Detail Component', () => {
    let comp: PgMessageModelDetailComponent;
    let fixture: ComponentFixture<PgMessageModelDetailComponent>;
    const route = ({ data: of({ pgMessageModel: new PgMessageModel(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgMessageModelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PgMessageModelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgMessageModelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pgMessageModel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
