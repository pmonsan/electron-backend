/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgMessageModelUpdateComponent } from 'app/entities/pg-message-model/pg-message-model-update.component';
import { PgMessageModelService } from 'app/entities/pg-message-model/pg-message-model.service';
import { PgMessageModel } from 'app/shared/model/pg-message-model.model';

describe('Component Tests', () => {
  describe('PgMessageModel Management Update Component', () => {
    let comp: PgMessageModelUpdateComponent;
    let fixture: ComponentFixture<PgMessageModelUpdateComponent>;
    let service: PgMessageModelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgMessageModelUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PgMessageModelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgMessageModelUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgMessageModelService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PgMessageModel(123);
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
        const entity = new PgMessageModel();
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
