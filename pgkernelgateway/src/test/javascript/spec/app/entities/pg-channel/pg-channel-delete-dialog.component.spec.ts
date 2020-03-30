/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgChannelDeleteDialogComponent } from 'app/entities/pg-channel/pg-channel-delete-dialog.component';
import { PgChannelService } from 'app/entities/pg-channel/pg-channel.service';

describe('Component Tests', () => {
  describe('PgChannel Management Delete Component', () => {
    let comp: PgChannelDeleteDialogComponent;
    let fixture: ComponentFixture<PgChannelDeleteDialogComponent>;
    let service: PgChannelService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgChannelDeleteDialogComponent]
      })
        .overrideTemplate(PgChannelDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgChannelDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgChannelService);
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
