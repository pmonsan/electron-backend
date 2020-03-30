/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgResponseCodeUpdateComponent } from 'app/entities/pg-response-code/pg-response-code-update.component';
import { PgResponseCodeService } from 'app/entities/pg-response-code/pg-response-code.service';
import { PgResponseCode } from 'app/shared/model/pg-response-code.model';

describe('Component Tests', () => {
  describe('PgResponseCode Management Update Component', () => {
    let comp: PgResponseCodeUpdateComponent;
    let fixture: ComponentFixture<PgResponseCodeUpdateComponent>;
    let service: PgResponseCodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgResponseCodeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PgResponseCodeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgResponseCodeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgResponseCodeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PgResponseCode(123);
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
        const entity = new PgResponseCode();
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
