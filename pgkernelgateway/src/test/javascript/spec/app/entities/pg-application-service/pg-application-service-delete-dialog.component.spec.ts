/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgApplicationServiceDeleteDialogComponent } from 'app/entities/pg-application-service/pg-application-service-delete-dialog.component';
import { PgApplicationServiceService } from 'app/entities/pg-application-service/pg-application-service.service';

describe('Component Tests', () => {
  describe('PgApplicationService Management Delete Component', () => {
    let comp: PgApplicationServiceDeleteDialogComponent;
    let fixture: ComponentFixture<PgApplicationServiceDeleteDialogComponent>;
    let service: PgApplicationServiceService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgApplicationServiceDeleteDialogComponent]
      })
        .overrideTemplate(PgApplicationServiceDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgApplicationServiceDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgApplicationServiceService);
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
