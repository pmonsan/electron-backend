/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ServiceLimitDeleteDialogComponent } from 'app/entities/service-limit/service-limit-delete-dialog.component';
import { ServiceLimitService } from 'app/entities/service-limit/service-limit.service';

describe('Component Tests', () => {
  describe('ServiceLimit Management Delete Component', () => {
    let comp: ServiceLimitDeleteDialogComponent;
    let fixture: ComponentFixture<ServiceLimitDeleteDialogComponent>;
    let service: ServiceLimitService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ServiceLimitDeleteDialogComponent]
      })
        .overrideTemplate(ServiceLimitDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceLimitDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceLimitService);
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
