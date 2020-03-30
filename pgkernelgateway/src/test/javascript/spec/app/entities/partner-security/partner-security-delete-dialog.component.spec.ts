/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PartnerSecurityDeleteDialogComponent } from 'app/entities/partner-security/partner-security-delete-dialog.component';
import { PartnerSecurityService } from 'app/entities/partner-security/partner-security.service';

describe('Component Tests', () => {
  describe('PartnerSecurity Management Delete Component', () => {
    let comp: PartnerSecurityDeleteDialogComponent;
    let fixture: ComponentFixture<PartnerSecurityDeleteDialogComponent>;
    let service: PartnerSecurityService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PartnerSecurityDeleteDialogComponent]
      })
        .overrideTemplate(PartnerSecurityDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PartnerSecurityDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartnerSecurityService);
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
