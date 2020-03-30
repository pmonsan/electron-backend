/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgApplicationUpdateComponent } from 'app/entities/pg-application/pg-application-update.component';
import { PgApplicationService } from 'app/entities/pg-application/pg-application.service';
import { PgApplication } from 'app/shared/model/pg-application.model';

describe('Component Tests', () => {
  describe('PgApplication Management Update Component', () => {
    let comp: PgApplicationUpdateComponent;
    let fixture: ComponentFixture<PgApplicationUpdateComponent>;
    let service: PgApplicationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgApplicationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PgApplicationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgApplicationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgApplicationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PgApplication(123);
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
        const entity = new PgApplication();
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
