/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { DetailTransactionDeleteDialogComponent } from 'app/entities/detail-transaction/detail-transaction-delete-dialog.component';
import { DetailTransactionService } from 'app/entities/detail-transaction/detail-transaction.service';

describe('Component Tests', () => {
  describe('DetailTransaction Management Delete Component', () => {
    let comp: DetailTransactionDeleteDialogComponent;
    let fixture: ComponentFixture<DetailTransactionDeleteDialogComponent>;
    let service: DetailTransactionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [DetailTransactionDeleteDialogComponent]
      })
        .overrideTemplate(DetailTransactionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DetailTransactionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DetailTransactionService);
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
