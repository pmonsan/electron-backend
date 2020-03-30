/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { CustomerLimitDeleteDialogComponent } from 'app/entities/customer-limit/customer-limit-delete-dialog.component';
import { CustomerLimitService } from 'app/entities/customer-limit/customer-limit.service';

describe('Component Tests', () => {
  describe('CustomerLimit Management Delete Component', () => {
    let comp: CustomerLimitDeleteDialogComponent;
    let fixture: ComponentFixture<CustomerLimitDeleteDialogComponent>;
    let service: CustomerLimitService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [CustomerLimitDeleteDialogComponent]
      })
        .overrideTemplate(CustomerLimitDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomerLimitDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerLimitService);
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
