/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgBatchDeleteDialogComponent } from 'app/entities/pg-batch/pg-batch-delete-dialog.component';
import { PgBatchService } from 'app/entities/pg-batch/pg-batch.service';

describe('Component Tests', () => {
  describe('PgBatch Management Delete Component', () => {
    let comp: PgBatchDeleteDialogComponent;
    let fixture: ComponentFixture<PgBatchDeleteDialogComponent>;
    let service: PgBatchService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgBatchDeleteDialogComponent]
      })
        .overrideTemplate(PgBatchDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgBatchDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgBatchService);
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
