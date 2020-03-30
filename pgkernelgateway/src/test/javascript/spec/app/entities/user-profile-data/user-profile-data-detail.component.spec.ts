/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { UserProfileDataDetailComponent } from 'app/entities/user-profile-data/user-profile-data-detail.component';
import { UserProfileData } from 'app/shared/model/user-profile-data.model';

describe('Component Tests', () => {
  describe('UserProfileData Management Detail Component', () => {
    let comp: UserProfileDataDetailComponent;
    let fixture: ComponentFixture<UserProfileDataDetailComponent>;
    const route = ({ data: of({ userProfileData: new UserProfileData(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [UserProfileDataDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserProfileDataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserProfileDataDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userProfileData).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
