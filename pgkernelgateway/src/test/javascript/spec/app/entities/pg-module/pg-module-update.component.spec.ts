/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgModuleUpdateComponent } from 'app/entities/pg-module/pg-module-update.component';
import { PgModuleService } from 'app/entities/pg-module/pg-module.service';
import { PgModule } from 'app/shared/model/pg-module.model';

describe('Component Tests', () => {
  describe('PgModule Management Update Component', () => {
    let comp: PgModuleUpdateComponent;
    let fixture: ComponentFixture<PgModuleUpdateComponent>;
    let service: PgModuleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgModuleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PgModuleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgModuleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgModuleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PgModule(123);
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
        const entity = new PgModule();
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
