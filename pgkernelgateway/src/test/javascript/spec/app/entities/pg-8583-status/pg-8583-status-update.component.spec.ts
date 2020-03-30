/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { Pg8583StatusUpdateComponent } from 'app/entities/pg-8583-status/pg-8583-status-update.component';
import { Pg8583StatusService } from 'app/entities/pg-8583-status/pg-8583-status.service';
import { Pg8583Status } from 'app/shared/model/pg-8583-status.model';

describe('Component Tests', () => {
  describe('Pg8583Status Management Update Component', () => {
    let comp: Pg8583StatusUpdateComponent;
    let fixture: ComponentFixture<Pg8583StatusUpdateComponent>;
    let service: Pg8583StatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [Pg8583StatusUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(Pg8583StatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Pg8583StatusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Pg8583StatusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pg8583Status(123);
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
        const entity = new Pg8583Status();
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
