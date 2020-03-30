/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ActivityAreaDeleteDialogComponent } from 'app/entities/activity-area/activity-area-delete-dialog.component';
import { ActivityAreaService } from 'app/entities/activity-area/activity-area.service';

describe('Component Tests', () => {
  describe('ActivityArea Management Delete Component', () => {
    let comp: ActivityAreaDeleteDialogComponent;
    let fixture: ComponentFixture<ActivityAreaDeleteDialogComponent>;
    let service: ActivityAreaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ActivityAreaDeleteDialogComponent]
      })
        .overrideTemplate(ActivityAreaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ActivityAreaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ActivityAreaService);
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
