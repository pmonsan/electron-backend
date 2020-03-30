/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ServiceChannelDeleteDialogComponent } from 'app/entities/service-channel/service-channel-delete-dialog.component';
import { ServiceChannelService } from 'app/entities/service-channel/service-channel.service';

describe('Component Tests', () => {
  describe('ServiceChannel Management Delete Component', () => {
    let comp: ServiceChannelDeleteDialogComponent;
    let fixture: ComponentFixture<ServiceChannelDeleteDialogComponent>;
    let service: ServiceChannelService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ServiceChannelDeleteDialogComponent]
      })
        .overrideTemplate(ServiceChannelDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceChannelDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceChannelService);
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
