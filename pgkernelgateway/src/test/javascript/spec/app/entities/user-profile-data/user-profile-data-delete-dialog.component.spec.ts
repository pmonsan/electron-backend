/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { UserProfileDataDeleteDialogComponent } from 'app/entities/user-profile-data/user-profile-data-delete-dialog.component';
import { UserProfileDataService } from 'app/entities/user-profile-data/user-profile-data.service';

describe('Component Tests', () => {
  describe('UserProfileData Management Delete Component', () => {
    let comp: UserProfileDataDeleteDialogComponent;
    let fixture: ComponentFixture<UserProfileDataDeleteDialogComponent>;
    let service: UserProfileDataService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [UserProfileDataDeleteDialogComponent]
      })
        .overrideTemplate(UserProfileDataDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserProfileDataDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserProfileDataService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
