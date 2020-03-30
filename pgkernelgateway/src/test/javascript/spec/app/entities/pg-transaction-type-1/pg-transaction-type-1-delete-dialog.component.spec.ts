/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgTransactionType1DeleteDialogComponent } from 'app/entities/pg-transaction-type-1/pg-transaction-type-1-delete-dialog.component';
import { PgTransactionType1Service } from 'app/entities/pg-transaction-type-1/pg-transaction-type-1.service';

describe('Component Tests', () => {
  describe('PgTransactionType1 Management Delete Component', () => {
    let comp: PgTransactionType1DeleteDialogComponent;
    let fixture: ComponentFixture<PgTransactionType1DeleteDialogComponent>;
    let service: PgTransactionType1Service;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgTransactionType1DeleteDialogComponent]
      })
        .overrideTemplate(PgTransactionType1DeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgTransactionType1DeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgTransactionType1Service);
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
