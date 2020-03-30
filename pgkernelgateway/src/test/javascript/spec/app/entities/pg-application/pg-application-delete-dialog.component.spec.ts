/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgApplicationDeleteDialogComponent } from 'app/entities/pg-application/pg-application-delete-dialog.component';
import { PgApplicationService } from 'app/entities/pg-application/pg-application.service';

describe('Component Tests', () => {
  describe('PgApplication Management Delete Component', () => {
    let comp: PgApplicationDeleteDialogComponent;
    let fixture: ComponentFixture<PgApplicationDeleteDialogComponent>;
    let service: PgApplicationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgApplicationDeleteDialogComponent]
      })
        .overrideTemplate(PgApplicationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgApplicationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgApplicationService);
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
