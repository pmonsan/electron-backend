/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionPriceDeleteDialogComponent } from 'app/entities/transaction-price/transaction-price-delete-dialog.component';
import { TransactionPriceService } from 'app/entities/transaction-price/transaction-price.service';

describe('Component Tests', () => {
  describe('TransactionPrice Management Delete Component', () => {
    let comp: TransactionPriceDeleteDialogComponent;
    let fixture: ComponentFixture<TransactionPriceDeleteDialogComponent>;
    let service: TransactionPriceService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionPriceDeleteDialogComponent]
      })
        .overrideTemplate(TransactionPriceDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionPriceDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionPriceService);
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
