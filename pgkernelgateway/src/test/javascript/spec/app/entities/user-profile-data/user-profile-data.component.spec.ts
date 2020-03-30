/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { UserProfileDataComponent } from 'app/entities/user-profile-data/user-profile-data.component';
import { UserProfileDataService } from 'app/entities/user-profile-data/user-profile-data.service';
import { UserProfileData } from 'app/shared/model/user-profile-data.model';

describe('Component Tests', () => {
  describe('UserProfileData Management Component', () => {
    let comp: UserProfileDataComponent;
    let fixture: ComponentFixture<UserProfileDataComponent>;
    let service: UserProfileDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [UserProfileDataComponent],
        providers: []
      })
        .overrideTemplate(UserProfileDataComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserProfileDataComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserProfileDataService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserProfileData(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userProfileData[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
