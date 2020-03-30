/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ServiceAuthenticationDeleteDialogComponent } from 'app/entities/service-authentication/service-authentication-delete-dialog.component';
import { ServiceAuthenticationService } from 'app/entities/service-authentication/service-authentication.service';

describe('Component Tests', () => {
  describe('ServiceAuthentication Management Delete Component', () => {
    let comp: ServiceAuthenticationDeleteDialogComponent;
    let fixture: ComponentFixture<ServiceAuthenticationDeleteDialogComponent>;
    let service: ServiceAuthenticationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ServiceAuthenticationDeleteDialogComponent]
      })
        .overrideTemplate(ServiceAuthenticationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceAuthenticationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceAuthenticationService);
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
