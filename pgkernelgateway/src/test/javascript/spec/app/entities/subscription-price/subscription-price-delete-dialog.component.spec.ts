/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { SubscriptionPriceDeleteDialogComponent } from 'app/entities/subscription-price/subscription-price-delete-dialog.component';
import { SubscriptionPriceService } from 'app/entities/subscription-price/subscription-price.service';

describe('Component Tests', () => {
  describe('SubscriptionPrice Management Delete Component', () => {
    let comp: SubscriptionPriceDeleteDialogComponent;
    let fixture: ComponentFixture<SubscriptionPriceDeleteDialogComponent>;
    let service: SubscriptionPriceService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [SubscriptionPriceDeleteDialogComponent]
      })
        .overrideTemplate(SubscriptionPriceDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SubscriptionPriceDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubscriptionPriceService);
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
