/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgResponseCodeDetailComponent } from 'app/entities/pg-response-code/pg-response-code-detail.component';
import { PgResponseCode } from 'app/shared/model/pg-response-code.model';

describe('Component Tests', () => {
  describe('PgResponseCode Management Detail Component', () => {
    let comp: PgResponseCodeDetailComponent;
    let fixture: ComponentFixture<PgResponseCodeDetailComponent>;
    const route = ({ data: of({ pgResponseCode: new PgResponseCode(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgResponseCodeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PgResponseCodeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgResponseCodeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pgResponseCode).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
