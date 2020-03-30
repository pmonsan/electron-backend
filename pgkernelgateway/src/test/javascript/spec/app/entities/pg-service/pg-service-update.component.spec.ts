/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgServiceUpdateComponent } from 'app/entities/pg-service/pg-service-update.component';
import { PgServiceService } from 'app/entities/pg-service/pg-service.service';
import { PgService } from 'app/shared/model/pg-service.model';

describe('Component Tests', () => {
  describe('PgService Management Update Component', () => {
    let comp: PgServiceUpdateComponent;
    let fixture: ComponentFixture<PgServiceUpdateComponent>;
    let service: PgServiceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgServiceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PgServiceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgServiceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgServiceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PgService(123);
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
        const entity = new PgService();
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
