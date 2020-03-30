/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { LimitMeasureDeleteDialogComponent } from 'app/entities/limit-measure/limit-measure-delete-dialog.component';
import { LimitMeasureService } from 'app/entities/limit-measure/limit-measure.service';

describe('Component Tests', () => {
  describe('LimitMeasure Management Delete Component', () => {
    let comp: LimitMeasureDeleteDialogComponent;
    let fixture: ComponentFixture<LimitMeasureDeleteDialogComponent>;
    let service: LimitMeasureService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [LimitMeasureDeleteDialogComponent]
      })
        .overrideTemplate(LimitMeasureDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LimitMeasureDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LimitMeasureService);
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
