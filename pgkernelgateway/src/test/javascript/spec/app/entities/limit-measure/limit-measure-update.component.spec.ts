/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { LimitMeasureUpdateComponent } from 'app/entities/limit-measure/limit-measure-update.component';
import { LimitMeasureService } from 'app/entities/limit-measure/limit-measure.service';
import { LimitMeasure } from 'app/shared/model/limit-measure.model';

describe('Component Tests', () => {
  describe('LimitMeasure Management Update Component', () => {
    let comp: LimitMeasureUpdateComponent;
    let fixture: ComponentFixture<LimitMeasureUpdateComponent>;
    let service: LimitMeasureService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [LimitMeasureUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LimitMeasureUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LimitMeasureUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LimitMeasureService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LimitMeasure(123);
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
        const entity = new LimitMeasure();
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
