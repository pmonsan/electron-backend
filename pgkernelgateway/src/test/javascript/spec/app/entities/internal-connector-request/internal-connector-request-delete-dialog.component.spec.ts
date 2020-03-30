/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { InternalConnectorRequestDeleteDialogComponent } from 'app/entities/internal-connector-request/internal-connector-request-delete-dialog.component';
import { InternalConnectorRequestService } from 'app/entities/internal-connector-request/internal-connector-request.service';

describe('Component Tests', () => {
  describe('InternalConnectorRequest Management Delete Component', () => {
    let comp: InternalConnectorRequestDeleteDialogComponent;
    let fixture: ComponentFixture<InternalConnectorRequestDeleteDialogComponent>;
    let service: InternalConnectorRequestService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [InternalConnectorRequestDeleteDialogComponent]
      })
        .overrideTemplate(InternalConnectorRequestDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InternalConnectorRequestDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InternalConnectorRequestService);
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
