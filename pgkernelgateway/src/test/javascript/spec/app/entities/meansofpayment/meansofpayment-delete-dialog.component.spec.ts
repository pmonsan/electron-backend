/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { MeansofpaymentDeleteDialogComponent } from 'app/entities/meansofpayment/meansofpayment-delete-dialog.component';
import { MeansofpaymentService } from 'app/entities/meansofpayment/meansofpayment.service';

describe('Component Tests', () => {
  describe('Meansofpayment Management Delete Component', () => {
    let comp: MeansofpaymentDeleteDialogComponent;
    let fixture: ComponentFixture<MeansofpaymentDeleteDialogComponent>;
    let service: MeansofpaymentService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [MeansofpaymentDeleteDialogComponent]
      })
        .overrideTemplate(MeansofpaymentDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MeansofpaymentDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MeansofpaymentService);
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
