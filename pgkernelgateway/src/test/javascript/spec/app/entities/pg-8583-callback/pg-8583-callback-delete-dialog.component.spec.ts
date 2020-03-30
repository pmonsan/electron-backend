/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { Pg8583CallbackDeleteDialogComponent } from 'app/entities/pg-8583-callback/pg-8583-callback-delete-dialog.component';
import { Pg8583CallbackService } from 'app/entities/pg-8583-callback/pg-8583-callback.service';

describe('Component Tests', () => {
  describe('Pg8583Callback Management Delete Component', () => {
    let comp: Pg8583CallbackDeleteDialogComponent;
    let fixture: ComponentFixture<Pg8583CallbackDeleteDialogComponent>;
    let service: Pg8583CallbackService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [Pg8583CallbackDeleteDialogComponent]
      })
        .overrideTemplate(Pg8583CallbackDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Pg8583CallbackDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Pg8583CallbackService);
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
