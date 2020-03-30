/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PartnerTypeDeleteDialogComponent } from 'app/entities/partner-type/partner-type-delete-dialog.component';
import { PartnerTypeService } from 'app/entities/partner-type/partner-type.service';

describe('Component Tests', () => {
  describe('PartnerType Management Delete Component', () => {
    let comp: PartnerTypeDeleteDialogComponent;
    let fixture: ComponentFixture<PartnerTypeDeleteDialogComponent>;
    let service: PartnerTypeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PartnerTypeDeleteDialogComponent]
      })
        .overrideTemplate(PartnerTypeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PartnerTypeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartnerTypeService);
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
