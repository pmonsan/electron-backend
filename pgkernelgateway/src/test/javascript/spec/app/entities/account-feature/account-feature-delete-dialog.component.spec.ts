/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { AccountFeatureDeleteDialogComponent } from 'app/entities/account-feature/account-feature-delete-dialog.component';
import { AccountFeatureService } from 'app/entities/account-feature/account-feature.service';

describe('Component Tests', () => {
  describe('AccountFeature Management Delete Component', () => {
    let comp: AccountFeatureDeleteDialogComponent;
    let fixture: ComponentFixture<AccountFeatureDeleteDialogComponent>;
    let service: AccountFeatureService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [AccountFeatureDeleteDialogComponent]
      })
        .overrideTemplate(AccountFeatureDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccountFeatureDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountFeatureService);
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
