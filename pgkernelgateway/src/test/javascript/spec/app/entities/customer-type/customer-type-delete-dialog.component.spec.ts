/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { CustomerTypeDeleteDialogComponent } from 'app/entities/customer-type/customer-type-delete-dialog.component';
import { CustomerTypeService } from 'app/entities/customer-type/customer-type.service';

describe('Component Tests', () => {
  describe('CustomerType Management Delete Component', () => {
    let comp: CustomerTypeDeleteDialogComponent;
    let fixture: ComponentFixture<CustomerTypeDeleteDialogComponent>;
    let service: CustomerTypeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [CustomerTypeDeleteDialogComponent]
      })
        .overrideTemplate(CustomerTypeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomerTypeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerTypeService);
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
