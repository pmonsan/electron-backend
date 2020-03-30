/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgMessageStatusUpdateComponent } from 'app/entities/pg-message-status/pg-message-status-update.component';
import { PgMessageStatusService } from 'app/entities/pg-message-status/pg-message-status.service';
import { PgMessageStatus } from 'app/shared/model/pg-message-status.model';

describe('Component Tests', () => {
  describe('PgMessageStatus Management Update Component', () => {
    let comp: PgMessageStatusUpdateComponent;
    let fixture: ComponentFixture<PgMessageStatusUpdateComponent>;
    let service: PgMessageStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgMessageStatusUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PgMessageStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgMessageStatusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgMessageStatusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PgMessageStatus(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PgMessageStatus();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
