/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { InternalConnectorStatusDeleteDialogComponent } from 'app/entities/internal-connector-status/internal-connector-status-delete-dialog.component';
import { InternalConnectorStatusService } from 'app/entities/internal-connector-status/internal-connector-status.service';

describe('Component Tests', () => {
  describe('InternalConnectorStatus Management Delete Component', () => {
    let comp: InternalConnectorStatusDeleteDialogComponent;
    let fixture: ComponentFixture<InternalConnectorStatusDeleteDialogComponent>;
    let service: InternalConnectorStatusService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [InternalConnectorStatusDeleteDialogComponent]
      })
        .overrideTemplate(InternalConnectorStatusDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InternalConnectorStatusDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InternalConnectorStatusService);
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
