/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgDetailMessageDetailComponent } from 'app/entities/pg-detail-message/pg-detail-message-detail.component';
import { PgDetailMessage } from 'app/shared/model/pg-detail-message.model';

describe('Component Tests', () => {
  describe('PgDetailMessage Management Detail Component', () => {
    let comp: PgDetailMessageDetailComponent;
    let fixture: ComponentFixture<PgDetailMessageDetailComponent>;
    const route = ({ data: of({ pgDetailMessage: new PgDetailMessage(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgDetailMessageDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PgDetailMessageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgDetailMessageDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pgDetailMessage).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
