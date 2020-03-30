/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgAccountDetailComponent } from 'app/entities/pg-account/pg-account-detail.component';
import { PgAccount } from 'app/shared/model/pg-account.model';

describe('Component Tests', () => {
  describe('PgAccount Management Detail Component', () => {
    let comp: PgAccountDetailComponent;
    let fixture: ComponentFixture<PgAccountDetailComponent>;
    const route = ({ data: of({ pgAccount: new PgAccount(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgAccountDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PgAccountDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgAccountDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pgAccount).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
