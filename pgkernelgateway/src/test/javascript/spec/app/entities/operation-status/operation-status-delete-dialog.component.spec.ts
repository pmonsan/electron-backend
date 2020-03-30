/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { OperationStatusDeleteDialogComponent } from 'app/entities/operation-status/operation-status-delete-dialog.component';
import { OperationStatusService } from 'app/entities/operation-status/operation-status.service';

describe('Component Tests', () => {
  describe('OperationStatus Management Delete Component', () => {
    let comp: OperationStatusDeleteDialogComponent;
    let fixture: ComponentFixture<OperationStatusDeleteDialogComponent>;
    let service: OperationStatusService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [OperationStatusDeleteDialogComponent]
      })
        .overrideTemplate(OperationStatusDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OperationStatusDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OperationStatusService);
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
