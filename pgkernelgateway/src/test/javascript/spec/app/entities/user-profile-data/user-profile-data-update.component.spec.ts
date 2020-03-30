/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { UserProfileDataUpdateComponent } from 'app/entities/user-profile-data/user-profile-data-update.component';
import { UserProfileDataService } from 'app/entities/user-profile-data/user-profile-data.service';
import { UserProfileData } from 'app/shared/model/user-profile-data.model';

describe('Component Tests', () => {
  describe('UserProfileData Management Update Component', () => {
    let comp: UserProfileDataUpdateComponent;
    let fixture: ComponentFixture<UserProfileDataUpdateComponent>;
    let service: UserProfileDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [UserProfileDataUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UserProfileDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserProfileDataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserProfileDataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserProfileData(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserProfileData();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
