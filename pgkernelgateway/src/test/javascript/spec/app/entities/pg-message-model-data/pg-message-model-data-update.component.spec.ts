/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgMessageModelDataUpdateComponent } from 'app/entities/pg-message-model-data/pg-message-model-data-update.component';
import { PgMessageModelDataService } from 'app/entities/pg-message-model-data/pg-message-model-data.service';
import { PgMessageModelData } from 'app/shared/model/pg-message-model-data.model';

describe('Component Tests', () => {
  describe('PgMessageModelData Management Update Component', () => {
    let comp: PgMessageModelDataUpdateComponent;
    let fixture: ComponentFixture<PgMessageModelDataUpdateComponent>;
    let service: PgMessageModelDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgMessageModelDataUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PgMessageModelDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgMessageModelDataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgMessageModelDataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PgMessageModelData(123);
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
        const entity = new PgMessageModelData();
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
