/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ForexUpdateComponent } from 'app/entities/forex/forex-update.component';
import { ForexService } from 'app/entities/forex/forex.service';
import { Forex } from 'app/shared/model/forex.model';

describe('Component Tests', () => {
  describe('Forex Management Update Component', () => {
    let comp: ForexUpdateComponent;
    let fixture: ComponentFixture<ForexUpdateComponent>;
    let service: ForexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ForexUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ForexUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ForexUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ForexService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Forex(123);
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
        const entity = new Forex();
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
