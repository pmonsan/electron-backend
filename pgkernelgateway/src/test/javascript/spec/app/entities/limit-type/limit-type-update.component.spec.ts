/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { LimitTypeUpdateComponent } from 'app/entities/limit-type/limit-type-update.component';
import { LimitTypeService } from 'app/entities/limit-type/limit-type.service';
import { LimitType } from 'app/shared/model/limit-type.model';

describe('Component Tests', () => {
  describe('LimitType Management Update Component', () => {
    let comp: LimitTypeUpdateComponent;
    let fixture: ComponentFixture<LimitTypeUpdateComponent>;
    let service: LimitTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [LimitTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LimitTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LimitTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LimitTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LimitType(123);
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
        const entity = new LimitType();
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
