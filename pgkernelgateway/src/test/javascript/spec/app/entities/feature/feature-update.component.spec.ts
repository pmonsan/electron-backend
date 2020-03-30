/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { FeatureUpdateComponent } from 'app/entities/feature/feature-update.component';
import { FeatureService } from 'app/entities/feature/feature.service';
import { Feature } from 'app/shared/model/feature.model';

describe('Component Tests', () => {
  describe('Feature Management Update Component', () => {
    let comp: FeatureUpdateComponent;
    let fixture: ComponentFixture<FeatureUpdateComponent>;
    let service: FeatureService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [FeatureUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FeatureUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FeatureUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FeatureService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Feature(123);
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
        const entity = new Feature();
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
