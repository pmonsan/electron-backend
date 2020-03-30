/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgDetailMessageDeleteDialogComponent } from 'app/entities/pg-detail-message/pg-detail-message-delete-dialog.component';
import { PgDetailMessageService } from 'app/entities/pg-detail-message/pg-detail-message.service';

describe('Component Tests', () => {
  describe('PgDetailMessage Management Delete Component', () => {
    let comp: PgDetailMessageDeleteDialogComponent;
    let fixture: ComponentFixture<PgDetailMessageDeleteDialogComponent>;
    let service: PgDetailMessageService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgDetailMessageDeleteDialogComponent]
      })
        .overrideTemplate(PgDetailMessageDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgDetailMessageDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgDetailMessageService);
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
