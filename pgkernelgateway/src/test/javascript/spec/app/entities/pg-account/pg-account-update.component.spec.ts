/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgAccountUpdateComponent } from 'app/entities/pg-account/pg-account-update.component';
import { PgAccountService } from 'app/entities/pg-account/pg-account.service';
import { PgAccount } from 'app/shared/model/pg-account.model';

describe('Component Tests', () => {
  describe('PgAccount Management Update Component', () => {
    let comp: PgAccountUpdateComponent;
    let fixture: ComponentFixture<PgAccountUpdateComponent>;
    let service: PgAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgAccountUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PgAccountUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgAccountUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgAccountService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PgAccount(123);
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
        const entity = new PgAccount();
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
