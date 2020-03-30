/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { UserConnectionDetailComponent } from 'app/entities/user-connection/user-connection-detail.component';
import { UserConnection } from 'app/shared/model/user-connection.model';

describe('Component Tests', () => {
  describe('UserConnection Management Detail Component', () => {
    let comp: UserConnectionDetailComponent;
    let fixture: ComponentFixture<UserConnectionDetailComponent>;
    const route = ({ data: of({ userConnection: new UserConnection(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [UserConnectionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserConnectionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserConnectionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userConnection).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
