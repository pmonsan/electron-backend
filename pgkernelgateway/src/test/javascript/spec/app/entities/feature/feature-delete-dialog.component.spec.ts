/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { FeatureDeleteDialogComponent } from 'app/entities/feature/feature-delete-dialog.component';
import { FeatureService } from 'app/entities/feature/feature.service';

describe('Component Tests', () => {
  describe('Feature Management Delete Component', () => {
    let comp: FeatureDeleteDialogComponent;
    let fixture: ComponentFixture<FeatureDeleteDialogComponent>;
    let service: FeatureService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [FeatureDeleteDialogComponent]
      })
        .overrideTemplate(FeatureDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FeatureDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FeatureService);
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
