/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionInfoDeleteDialogComponent } from 'app/entities/transaction-info/transaction-info-delete-dialog.component';
import { TransactionInfoService } from 'app/entities/transaction-info/transaction-info.service';

describe('Component Tests', () => {
  describe('TransactionInfo Management Delete Component', () => {
    let comp: TransactionInfoDeleteDialogComponent;
    let fixture: ComponentFixture<TransactionInfoDeleteDialogComponent>;
    let service: TransactionInfoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionInfoDeleteDialogComponent]
      })
        .overrideTemplate(TransactionInfoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionInfoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionInfoService);
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
