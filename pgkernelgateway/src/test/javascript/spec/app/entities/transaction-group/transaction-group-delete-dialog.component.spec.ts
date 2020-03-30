/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionGroupDeleteDialogComponent } from 'app/entities/transaction-group/transaction-group-delete-dialog.component';
import { TransactionGroupService } from 'app/entities/transaction-group/transaction-group.service';

describe('Component Tests', () => {
  describe('TransactionGroup Management Delete Component', () => {
    let comp: TransactionGroupDeleteDialogComponent;
    let fixture: ComponentFixture<TransactionGroupDeleteDialogComponent>;
    let service: TransactionGroupService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionGroupDeleteDialogComponent]
      })
        .overrideTemplate(TransactionGroupDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionGroupDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionGroupService);
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
