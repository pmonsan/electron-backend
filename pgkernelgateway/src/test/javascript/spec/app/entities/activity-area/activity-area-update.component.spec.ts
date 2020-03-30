/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ActivityAreaUpdateComponent } from 'app/entities/activity-area/activity-area-update.component';
import { ActivityAreaService } from 'app/entities/activity-area/activity-area.service';
import { ActivityArea } from 'app/shared/model/activity-area.model';

describe('Component Tests', () => {
  describe('ActivityArea Management Update Component', () => {
    let comp: ActivityAreaUpdateComponent;
    let fixture: ComponentFixture<ActivityAreaUpdateComponent>;
    let service: ActivityAreaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ActivityAreaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ActivityAreaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ActivityAreaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ActivityAreaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ActivityArea(123);
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
        const entity = new ActivityArea();
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
