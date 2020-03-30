/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgMessageStatusDeleteDialogComponent } from 'app/entities/pg-message-status/pg-message-status-delete-dialog.component';
import { PgMessageStatusService } from 'app/entities/pg-message-status/pg-message-status.service';

describe('Component Tests', () => {
  describe('PgMessageStatus Management Delete Component', () => {
    let comp: PgMessageStatusDeleteDialogComponent;
    let fixture: ComponentFixture<PgMessageStatusDeleteDialogComponent>;
    let service: PgMessageStatusService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgMessageStatusDeleteDialogComponent]
      })
        .overrideTemplate(PgMessageStatusDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgMessageStatusDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgMessageStatusService);
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
