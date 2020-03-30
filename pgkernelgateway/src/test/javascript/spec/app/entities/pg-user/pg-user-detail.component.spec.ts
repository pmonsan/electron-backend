/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgUserDetailComponent } from 'app/entities/pg-user/pg-user-detail.component';
import { PgUser } from 'app/shared/model/pg-user.model';

describe('Component Tests', () => {
  describe('PgUser Management Detail Component', () => {
    let comp: PgUserDetailComponent;
    let fixture: ComponentFixture<PgUserDetailComponent>;
    const route = ({ data: of({ pgUser: new PgUser(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgUserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PgUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pgUser).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
