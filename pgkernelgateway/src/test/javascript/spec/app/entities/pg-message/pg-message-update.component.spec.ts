/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgMessageUpdateComponent } from 'app/entities/pg-message/pg-message-update.component';
import { PgMessageService } from 'app/entities/pg-message/pg-message.service';
import { PgMessage } from 'app/shared/model/pg-message.model';

describe('Component Tests', () => {
  describe('PgMessage Management Update Component', () => {
    let comp: PgMessageUpdateComponent;
    let fixture: ComponentFixture<PgMessageUpdateComponent>;
    let service: PgMessageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgMessageUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PgMessageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgMessageUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgMessageService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PgMessage(123);
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
        const entity = new PgMessage();
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
