/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { UserProfileDetailComponent } from 'app/entities/user-profile/user-profile-detail.component';
import { UserProfile } from 'app/shared/model/user-profile.model';

describe('Component Tests', () => {
  describe('UserProfile Management Detail Component', () => {
    let comp: UserProfileDetailComponent;
    let fixture: ComponentFixture<UserProfileDetailComponent>;
    const route = ({ data: of({ userProfile: new UserProfile(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [UserProfileDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserProfileDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserProfileDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userProfile).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
