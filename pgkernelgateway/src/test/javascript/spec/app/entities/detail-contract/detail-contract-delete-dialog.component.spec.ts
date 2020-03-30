/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { DetailContractDeleteDialogComponent } from 'app/entities/detail-contract/detail-contract-delete-dialog.component';
import { DetailContractService } from 'app/entities/detail-contract/detail-contract.service';

describe('Component Tests', () => {
  describe('DetailContract Management Delete Component', () => {
    let comp: DetailContractDeleteDialogComponent;
    let fixture: ComponentFixture<DetailContractDeleteDialogComponent>;
    let service: DetailContractService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [DetailContractDeleteDialogComponent]
      })
        .overrideTemplate(DetailContractDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DetailContractDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DetailContractService);
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
