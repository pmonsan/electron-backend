/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgUserUpdateComponent } from 'app/entities/pg-user/pg-user-update.component';
import { PgUserService } from 'app/entities/pg-user/pg-user.service';
import { PgUser } from 'app/shared/model/pg-user.model';

describe('Component Tests', () => {
  describe('PgUser Management Update Component', () => {
    let comp: PgUserUpdateComponent;
    let fixture: ComponentFixture<PgUserUpdateComponent>;
    let service: PgUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgUserUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PgUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgUserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgUserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PgUser(123);
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
        const entity = new PgUser();
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
