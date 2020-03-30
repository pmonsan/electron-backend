/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { UserConnectionDeleteDialogComponent } from 'app/entities/user-connection/user-connection-delete-dialog.component';
import { UserConnectionService } from 'app/entities/user-connection/user-connection.service';

describe('Component Tests', () => {
  describe('UserConnection Management Delete Component', () => {
    let comp: UserConnectionDeleteDialogComponent;
    let fixture: ComponentFixture<UserConnectionDeleteDialogComponent>;
    let service: UserConnectionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [UserConnectionDeleteDialogComponent]
      })
        .overrideTemplate(UserConnectionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserConnectionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserConnectionService);
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
