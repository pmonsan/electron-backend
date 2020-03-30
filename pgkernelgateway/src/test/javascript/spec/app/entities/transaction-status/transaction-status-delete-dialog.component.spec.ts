/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionStatusDeleteDialogComponent } from 'app/entities/transaction-status/transaction-status-delete-dialog.component';
import { TransactionStatusService } from 'app/entities/transaction-status/transaction-status.service';

describe('Component Tests', () => {
  describe('TransactionStatus Management Delete Component', () => {
    let comp: TransactionStatusDeleteDialogComponent;
    let fixture: ComponentFixture<TransactionStatusDeleteDialogComponent>;
    let service: TransactionStatusService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionStatusDeleteDialogComponent]
      })
        .overrideTemplate(TransactionStatusDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionStatusDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionStatusService);
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
