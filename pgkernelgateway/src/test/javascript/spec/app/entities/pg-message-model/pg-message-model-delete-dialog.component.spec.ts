/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgMessageModelDeleteDialogComponent } from 'app/entities/pg-message-model/pg-message-model-delete-dialog.component';
import { PgMessageModelService } from 'app/entities/pg-message-model/pg-message-model.service';

describe('Component Tests', () => {
  describe('PgMessageModel Management Delete Component', () => {
    let comp: PgMessageModelDeleteDialogComponent;
    let fixture: ComponentFixture<PgMessageModelDeleteDialogComponent>;
    let service: PgMessageModelService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgMessageModelDeleteDialogComponent]
      })
        .overrideTemplate(PgMessageModelDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgMessageModelDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgMessageModelService);
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
