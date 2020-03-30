/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgTransactionType2DeleteDialogComponent } from 'app/entities/pg-transaction-type-2/pg-transaction-type-2-delete-dialog.component';
import { PgTransactionType2Service } from 'app/entities/pg-transaction-type-2/pg-transaction-type-2.service';

describe('Component Tests', () => {
  describe('PgTransactionType2 Management Delete Component', () => {
    let comp: PgTransactionType2DeleteDialogComponent;
    let fixture: ComponentFixture<PgTransactionType2DeleteDialogComponent>;
    let service: PgTransactionType2Service;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgTransactionType2DeleteDialogComponent]
      })
        .overrideTemplate(PgTransactionType2DeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgTransactionType2DeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgTransactionType2Service);
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
