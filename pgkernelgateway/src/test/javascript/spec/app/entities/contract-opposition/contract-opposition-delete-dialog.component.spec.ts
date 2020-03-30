/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ContractOppositionDeleteDialogComponent } from 'app/entities/contract-opposition/contract-opposition-delete-dialog.component';
import { ContractOppositionService } from 'app/entities/contract-opposition/contract-opposition.service';

describe('Component Tests', () => {
  describe('ContractOpposition Management Delete Component', () => {
    let comp: ContractOppositionDeleteDialogComponent;
    let fixture: ComponentFixture<ContractOppositionDeleteDialogComponent>;
    let service: ContractOppositionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ContractOppositionDeleteDialogComponent]
      })
        .overrideTemplate(ContractOppositionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContractOppositionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContractOppositionService);
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
