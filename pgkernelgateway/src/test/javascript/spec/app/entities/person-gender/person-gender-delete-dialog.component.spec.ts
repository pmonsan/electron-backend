/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PersonGenderDeleteDialogComponent } from 'app/entities/person-gender/person-gender-delete-dialog.component';
import { PersonGenderService } from 'app/entities/person-gender/person-gender.service';

describe('Component Tests', () => {
  describe('PersonGender Management Delete Component', () => {
    let comp: PersonGenderDeleteDialogComponent;
    let fixture: ComponentFixture<PersonGenderDeleteDialogComponent>;
    let service: PersonGenderService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PersonGenderDeleteDialogComponent]
      })
        .overrideTemplate(PersonGenderDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PersonGenderDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonGenderService);
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
