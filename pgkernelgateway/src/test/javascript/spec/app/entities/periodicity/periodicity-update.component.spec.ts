/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PeriodicityUpdateComponent } from 'app/entities/periodicity/periodicity-update.component';
import { PeriodicityService } from 'app/entities/periodicity/periodicity.service';
import { Periodicity } from 'app/shared/model/periodicity.model';

describe('Component Tests', () => {
  describe('Periodicity Management Update Component', () => {
    let comp: PeriodicityUpdateComponent;
    let fixture: ComponentFixture<PeriodicityUpdateComponent>;
    let service: PeriodicityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PeriodicityUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PeriodicityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PeriodicityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PeriodicityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Periodicity(123);
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
        const entity = new Periodicity();
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
