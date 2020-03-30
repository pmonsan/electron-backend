/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgMessageDetailComponent } from 'app/entities/pg-message/pg-message-detail.component';
import { PgMessage } from 'app/shared/model/pg-message.model';

describe('Component Tests', () => {
  describe('PgMessage Management Detail Component', () => {
    let comp: PgMessageDetailComponent;
    let fixture: ComponentFixture<PgMessageDetailComponent>;
    const route = ({ data: of({ pgMessage: new PgMessage(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgMessageDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PgMessageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgMessageDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pgMessage).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
