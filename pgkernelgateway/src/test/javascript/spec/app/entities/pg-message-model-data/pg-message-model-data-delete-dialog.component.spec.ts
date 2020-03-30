/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgMessageModelDataDeleteDialogComponent } from 'app/entities/pg-message-model-data/pg-message-model-data-delete-dialog.component';
import { PgMessageModelDataService } from 'app/entities/pg-message-model-data/pg-message-model-data.service';

describe('Component Tests', () => {
  describe('PgMessageModelData Management Delete Component', () => {
    let comp: PgMessageModelDataDeleteDialogComponent;
    let fixture: ComponentFixture<PgMessageModelDataDeleteDialogComponent>;
    let service: PgMessageModelDataService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgMessageModelDataDeleteDialogComponent]
      })
        .overrideTemplate(PgMessageModelDataDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgMessageModelDataDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgMessageModelDataService);
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
