/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { Pg8583CallbackUpdateComponent } from 'app/entities/pg-8583-callback/pg-8583-callback-update.component';
import { Pg8583CallbackService } from 'app/entities/pg-8583-callback/pg-8583-callback.service';
import { Pg8583Callback } from 'app/shared/model/pg-8583-callback.model';

describe('Component Tests', () => {
  describe('Pg8583Callback Management Update Component', () => {
    let comp: Pg8583CallbackUpdateComponent;
    let fixture: ComponentFixture<Pg8583CallbackUpdateComponent>;
    let service: Pg8583CallbackService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [Pg8583CallbackUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(Pg8583CallbackUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Pg8583CallbackUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Pg8583CallbackService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pg8583Callback(123);
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
        const entity = new Pg8583Callback();
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
