/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { UserProfileDeleteDialogComponent } from 'app/entities/user-profile/user-profile-delete-dialog.component';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';

describe('Component Tests', () => {
  describe('UserProfile Management Delete Component', () => {
    let comp: UserProfileDeleteDialogComponent;
    let fixture: ComponentFixture<UserProfileDeleteDialogComponent>;
    let service: UserProfileService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [UserProfileDeleteDialogComponent]
      })
        .overrideTemplate(UserProfileDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserProfileDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserProfileService);
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
