/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgChannelAuthorizedDeleteDialogComponent } from 'app/entities/pg-channel-authorized/pg-channel-authorized-delete-dialog.component';
import { PgChannelAuthorizedService } from 'app/entities/pg-channel-authorized/pg-channel-authorized.service';

describe('Component Tests', () => {
  describe('PgChannelAuthorized Management Delete Component', () => {
    let comp: PgChannelAuthorizedDeleteDialogComponent;
    let fixture: ComponentFixture<PgChannelAuthorizedDeleteDialogComponent>;
    let service: PgChannelAuthorizedService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgChannelAuthorizedDeleteDialogComponent]
      })
        .overrideTemplate(PgChannelAuthorizedDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgChannelAuthorizedDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgChannelAuthorizedService);
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
