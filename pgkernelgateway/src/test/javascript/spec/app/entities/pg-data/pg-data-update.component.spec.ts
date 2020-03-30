/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgDataUpdateComponent } from 'app/entities/pg-data/pg-data-update.component';
import { PgDataService } from 'app/entities/pg-data/pg-data.service';
import { PgData } from 'app/shared/model/pg-data.model';

describe('Component Tests', () => {
  describe('PgData Management Update Component', () => {
    let comp: PgDataUpdateComponent;
    let fixture: ComponentFixture<PgDataUpdateComponent>;
    let service: PgDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgDataUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PgDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgDataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgDataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PgData(123);
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
        const entity = new PgData();
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
