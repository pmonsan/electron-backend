/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { Pg8583StatusDeleteDialogComponent } from 'app/entities/pg-8583-status/pg-8583-status-delete-dialog.component';
import { Pg8583StatusService } from 'app/entities/pg-8583-status/pg-8583-status.service';

describe('Component Tests', () => {
  describe('Pg8583Status Management Delete Component', () => {
    let comp: Pg8583StatusDeleteDialogComponent;
    let fixture: ComponentFixture<Pg8583StatusDeleteDialogComponent>;
    let service: Pg8583StatusService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [Pg8583StatusDeleteDialogComponent]
      })
        .overrideTemplate(Pg8583StatusDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Pg8583StatusDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Pg8583StatusService);
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
