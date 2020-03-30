/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionCommissionDeleteDialogComponent } from 'app/entities/transaction-commission/transaction-commission-delete-dialog.component';
import { TransactionCommissionService } from 'app/entities/transaction-commission/transaction-commission.service';

describe('Component Tests', () => {
  describe('TransactionCommission Management Delete Component', () => {
    let comp: TransactionCommissionDeleteDialogComponent;
    let fixture: ComponentFixture<TransactionCommissionDeleteDialogComponent>;
    let service: TransactionCommissionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionCommissionDeleteDialogComponent]
      })
        .overrideTemplate(TransactionCommissionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionCommissionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionCommissionService);
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
