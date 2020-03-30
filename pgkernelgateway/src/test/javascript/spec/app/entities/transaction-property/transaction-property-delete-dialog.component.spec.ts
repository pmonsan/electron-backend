/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionPropertyDeleteDialogComponent } from 'app/entities/transaction-property/transaction-property-delete-dialog.component';
import { TransactionPropertyService } from 'app/entities/transaction-property/transaction-property.service';

describe('Component Tests', () => {
  describe('TransactionProperty Management Delete Component', () => {
    let comp: TransactionPropertyDeleteDialogComponent;
    let fixture: ComponentFixture<TransactionPropertyDeleteDialogComponent>;
    let service: TransactionPropertyService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionPropertyDeleteDialogComponent]
      })
        .overrideTemplate(TransactionPropertyDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionPropertyDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionPropertyService);
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
