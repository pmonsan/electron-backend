/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { TransactionChannelDeleteDialogComponent } from 'app/entities/transaction-channel/transaction-channel-delete-dialog.component';
import { TransactionChannelService } from 'app/entities/transaction-channel/transaction-channel.service';

describe('Component Tests', () => {
  describe('TransactionChannel Management Delete Component', () => {
    let comp: TransactionChannelDeleteDialogComponent;
    let fixture: ComponentFixture<TransactionChannelDeleteDialogComponent>;
    let service: TransactionChannelService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [TransactionChannelDeleteDialogComponent]
      })
        .overrideTemplate(TransactionChannelDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionChannelDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionChannelService);
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
