/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgApplicationServiceUpdateComponent } from 'app/entities/pg-application-service/pg-application-service-update.component';
import { PgApplicationServiceService } from 'app/entities/pg-application-service/pg-application-service.service';
import { PgApplicationService } from 'app/shared/model/pg-application-service.model';

describe('Component Tests', () => {
  describe('PgApplicationService Management Update Component', () => {
    let comp: PgApplicationServiceUpdateComponent;
    let fixture: ComponentFixture<PgApplicationServiceUpdateComponent>;
    let service: PgApplicationServiceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgApplicationServiceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PgApplicationServiceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgApplicationServiceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgApplicationServiceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PgApplicationService(123);
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
        const entity = new PgApplicationService();
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
